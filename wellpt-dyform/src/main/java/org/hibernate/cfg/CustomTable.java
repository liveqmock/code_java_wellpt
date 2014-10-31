/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Constraint;
import org.hibernate.mapping.UniqueKey;
import org.hibernate.tool.hbm2ddl.ColumnMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.dyform.service.DyFormDataService;

/**
 * A relational table
 *
 * @author Gavin King
 */
public class CustomTable extends org.hibernate.mapping.Table {

	public Iterator sqlAlterStrings(Dialect dialect, Mapping p, TableMetadata tableInfo, String defaultCatalog,
			String defaultSchema) throws HibernateException {

		StringBuilder root = new StringBuilder("alter table ").append(
				getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(' ');

		List results = new ArrayList();
		String tblName = tableInfo.getName();

		{//添加字段、修改字段类型，以及修改字段名，修改字段长度
			Iterator iter = getColumnIterator();
			while (iter.hasNext()) {
				Column column = (Column) iter.next();
				String name = column.getQuotedName(dialect);
				String oldName = getOldName(column);

				ColumnMetadata columnInfo = tableInfo.getColumnMetadata(column.getName());//this column is loaded from db

				// the column doesnt exist at all. 
				if (columnInfo == null
						&& (oldName == null || oldName.trim().length() == 0 || name.trim().equalsIgnoreCase(
								oldName.trim()))) {
					StringBuilder alter = new StringBuilder(root.toString()).append(dialect.getAddColumnString())
							.append(' ').append(column.getQuotedName(dialect)).append(' ')
							.append(column.getSqlType(dialect, p));
					String defaultValue = column.getDefaultValue();
					if (defaultValue != null) {
						alter.append(" default ").append(defaultValue);
					}

					if (column.isNullable()) {
						alter.append(dialect.getNullColumnString());
					} else {
						alter.append(" not null");
					}

					if (column.isUnique()) {
						String keyName = Constraint.generateName("UK_", this, column);
						UniqueKey uk = getOrCreateUniqueKey(keyName);
						uk.addColumn(column);
						alter.append(dialect.getUniqueDelegate().getColumnDefinitionUniquenessFragment(column));
					}

					if (column.hasCheckConstraint() && dialect.supportsColumnCheck()) {
						alter.append(" check(").append(column.getCheckConstraint()).append(")");
					}

					String columnComment = column.getComment();
					if (columnComment != null) {
						alter.append(dialect.getColumnComment(columnComment));
					}

					alter.append(dialect.getAddColumnSuffixString());

					results.add(alter.toString());
				} else {

					//修改字段名 
					if (oldName == null || oldName.trim().length() == 0 || name.trim().equalsIgnoreCase(oldName.trim())) {
					} else {//更新字段名
						StringBuilder alter = new StringBuilder(root.toString()).append(" rename column  ").append(' ')
								.append(oldName).append(' ').append(" to ").append(column.getQuotedName(dialect));
						results.add(alter.toString());
						columnInfo = tableInfo.getColumnMetadata(oldName);//this column is loaded from db
					}

					//修改字段长度及类型
					int typeCode1 = columnInfo.getTypeCode();
					int typeCode2 = column.getSqlTypeCode(p);

					String srcType = columnInfo.getTypeName().toLowerCase();
					String destType = column.getSqlType(dialect, p).toLowerCase();
					boolean typesMatch = (destType.startsWith(srcType) || typeCode1 == typeCode2);
					if ((destType.indexOf("double") != -1 && srcType.indexOf("float") != -1)) {
						typesMatch = true;//oracle中double,float都是表现为float
					}

					if (!typesMatch
							|| (column.getSqlType(dialect, p).toLowerCase().indexOf("char") > 0 && columnInfo
									.getColumnSize() != column.getLength())) {//type of column is changed,or string length is changed

						if (!typesMatch) {//数据类型要做改变，列必须没有数据
							DyFormDataService dyFormDataService = (DyFormDataService) ApplicationContextHolder
									.getBean("dyFormDataService");
							long count = dyFormDataService.queryTotalCountOfFormDataOfMainform(tblName,
									Restrictions.isNotNull(name));//表示有数据
							if (count > 0) {
								throw new HibernateException("field[" + name + "] has " + count + " data in table["
										+ tblName + "], cannot change its type");
							}

						}
						StringBuilder alter = new StringBuilder(root.toString()).append(" modify ").append(' ')
								.append(column.getQuotedName(dialect)).append(' ')
								.append(column.getSqlType(dialect, p));
						results.add(alter.toString());
					}

				}
			}
		}

		{//删除字段

			String commment = this.getComment();
			if (commment == null) {
				return results.iterator();
			}
			try {
				JSONObject commentJSON = new JSONObject(commment);
				JSONArray delFieldNamesJson = commentJSON.getJSONArray("delFieldNames");
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < delFieldNamesJson.length(); i++) {
					String fieldName = delFieldNamesJson.getString(i);
					if (map.containsKey(fieldName.toLowerCase())) {//排除重复
						continue;
					}
					map.put(fieldName.toLowerCase(), null);
					ColumnMetadata columnInfo = tableInfo.getColumnMetadata(fieldName);//this column is loaded from db
					if (columnInfo == null) {
						System.out.println("fieldName[" + fieldName + "] is not exist in tbl " + tableInfo.getName());
						continue;
					}
					DyFormDataService dyFormDataService = (DyFormDataService) ApplicationContextHolder
							.getBean("dyFormDataService");
					long count = dyFormDataService.queryTotalCountOfFormDataOfMainform(tblName,
							Restrictions.isNotNull(fieldName));//表示有数据
					if (count > 0) {
						throw new HibernateException("field[" + fieldName + "] has " + count + " data in table["
								+ tblName + "], cannot delete it");
					}
					StringBuffer alter = new StringBuffer(root.toString()).append(" drop column ").append(' ')
							.append(fieldName);
					results.add(alter.toString());
				}
			} catch (JSONException e1) {
				return results.iterator();
			}
		}

		return results.iterator();
	}

	private String getOldName(Column column) {
		String comment = column.getComment();
		String oldName = null;
		if (comment != null && comment.trim().length() > 0) {
			try {
				JSONObject commentJSON = new JSONObject(comment);
				oldName = commentJSON.getString("oldName");//字段最原来的字段名  
			} catch (JSONException e) {
				//e.printStackTrace();
			}
		}

		return oldName == null ? column.getName() : oldName;

	}

}
