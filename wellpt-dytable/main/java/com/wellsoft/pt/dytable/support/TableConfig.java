package com.wellsoft.pt.dytable.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentifierGeneratorAggregator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.util.StringHelper;
import org.hibernate.internal.util.collections.ArrayHelper;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.IdentifierCollection;
import org.hibernate.mapping.Index;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.ColumnMetadata;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.ForeignKeyMetadata;
import org.hibernate.tool.hbm2ddl.IndexMetadata;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wellsoft.pt.core.context.ApplicationContextHolder;
import com.wellsoft.pt.core.resource.Config;

public class TableConfig {
	private static final Logger log = LoggerFactory.getLogger(TableConfig.class);

	private Configuration config;
	private Dialect dialect;
	private SqlStatementLogger sqlStatementLogger;

	private Mapping mapping;
	//这里利用hbmdll来简化部分处理
	//	private SchemaExport export;
	//	private SchemaUpdate update;
	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = ApplicationContextHolder.getBean(Config.TENANT_SESSION_FACTORY_BEAN_NAME,
					SessionFactory.class);
		}
		return sessionFactory;
	}

	public TableConfig(Configuration cfg) {
		this.config = cfg;
		dialect = ((SessionFactoryImpl) getSessionFactory()).getDialect();
		sqlStatementLogger = ((SessionFactoryImpl) getSessionFactory()).getServiceRegistry()
				.getService(JdbcServices.class).getSqlStatementLogger();
		initMapping();
	}

	/**
	 * 
	* @Title: initMapping
	* @Description:将mapping初始化
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	private void initMapping() {
		Field mappingField;
		try {
			mappingField = config.getClass().getDeclaredField("mapping");
			mappingField.setAccessible(true);
			mapping = (Mapping) (mappingField.get(config));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addTable() {
		//生成数据库表，这里只创建数据表
		CustomSchemaExport customSchemaExport = new CustomSchemaExport(
				((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		customSchemaExport.execute(true, true, false, true);
		//		SchemaExport export = new SchemaExport(((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		//		export.execute(true, true, false, true);
	}

	public void deleteTable() {
		//将涉及到数据表进行删除
		CustomSchemaExport customSchemaExport = new CustomSchemaExport(
				((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		customSchemaExport.execute(true, true, true, false);
		//		SchemaExport export = new SchemaExport(((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		//		export.execute(true, true, true, false);
	}

	/**
	 * 
	* @Title: updateTable
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void updateTable() {
		CustomSchemaUpdate schemaUpdate = new CustomSchemaUpdate(
				((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);
		schemaUpdate.execute(true, true);
	}

	//	/**
	//	 * 
	//	* @Title: updateColumn
	//	* @Description: 更改某一列 类型
	//	* @param     设定文件
	//	* @return void    返回类型
	//	* @throws
	//	 */
	//	public void updateColumnType() {
	//
	//	}
	//
	//	public void updateColumnName() {
	//
	//	}

	/**
	 * 
	* @Title: deleteColumn
	* @Description: 删除某一列
	* @param     设定文件
	* @return void    返回类型
	* @throws
	//	 */
	//	public void deleteColumn() {
	//
	//	}

	/**
	 * 
	* @Title: addColumn
	* @Description: 添加某一列，可以利用exportupdate来做
	* @param     设定文件
	* @return void    返回类型
	* @throws
	 */
	public void addColumn() {
		//会比较两个cfg 将新添加的column添加进来
		SchemaUpdate update = new SchemaUpdate(((SessionFactoryImpl) getSessionFactory()).getServiceRegistry(), config);

		//执行添加，不打印
		update.execute(false, true);
	}

	/**
	 * 
	* @Title: generateSchemaUpdateScript
	* @Description: 调整configuration 中获取更改数据表字段的处理
	* @param @param dialect
	* @param @param databaseMetadata
	* @param @return
	* @param @throws HibernateException    设定文件
	* @return String[]    返回类型
	* @throws
	 */
	private String[] generateSchemaUpdateScript(DatabaseMetadata databaseMetadata) throws HibernateException {
		config.buildMappings();
		//		Dialect dialect = ((SessionFactoryImpl) getSessionFactory()).getDialect();

		String defaultCatalog = config.getProperty(Environment.DEFAULT_CATALOG);
		String defaultSchema = config.getProperty(Environment.DEFAULT_SCHEMA);

		ArrayList script = new ArrayList(50);

		Iterator iter = config.getTableMappings();
		while (iter.hasNext()) {
			Table table = (Table) iter.next();
			if (table.isPhysicalTable()) {
				TableMetadata tableInfo = databaseMetadata.getTableMetadata(table.getName(),
						(table.getSchema() == null) ? defaultSchema : table.getSchema(),
						(table.getCatalog() == null) ? defaultCatalog : table.getCatalog(), table.isQuoted()

				);
				if (tableInfo == null) {
					script.add(table.sqlCreateString(dialect, mapping, defaultCatalog, defaultSchema));
				} else {
					Iterator subiter = sqlAlterStrings(table, dialect, mapping, tableInfo, defaultCatalog,
							defaultSchema);
					while (subiter.hasNext()) {
						script.add(subiter.next());
					}
				}
				Iterator comments = table.sqlCommentStrings(dialect, defaultCatalog, defaultSchema);
				while (comments.hasNext()) {
					script.add(comments.next());
				}
			}
		}

		iter = config.getTableMappings();
		while (iter.hasNext()) {
			Table table = (Table) iter.next();
			if (table.isPhysicalTable()) {

				TableMetadata tableInfo = databaseMetadata.getTableMetadata(table.getName(), table.getSchema(),
						table.getCatalog(), table.isQuoted());

				if (dialect.hasAlterTable()) {
					Iterator subIter = table.getForeignKeyIterator();
					while (subIter.hasNext()) {
						ForeignKey fk = (ForeignKey) subIter.next();
						if (fk.isPhysicalConstraint()) {
							boolean create = tableInfo == null || (tableInfo.getForeignKeyMetadata(fk) == null && (
							//Icky workaround for MySQL bug:
									!(dialect instanceof MySQLDialect) || tableInfo.getIndexMetadata(fk.getName()) == null));
							if (create) {
								script.add(fk.sqlCreateString(dialect, mapping, defaultCatalog, defaultSchema));
							}
						}
					}
				}

				Iterator subIter = table.getIndexIterator();
				while (subIter.hasNext()) {
					final Index index = (Index) subIter.next();
					// Skip if index already exists
					if (tableInfo != null && StringHelper.isNotEmpty(index.getName())) {
						final IndexMetadata meta = tableInfo.getIndexMetadata(index.getName());
						if (meta != null) {
							continue;
						}
					}
					script.add(index.sqlCreateString(dialect, mapping, defaultCatalog, defaultSchema));
				}

				//broken, 'cos we don't generate these with names in SchemaExport
				//				subIter = table.getUniqueKeyIterator();
				//				while ( subIter.hasNext() ) {
				//					UniqueKey uk = (UniqueKey) subIter.next();
				//					if ( tableInfo==null || tableInfo.getIndexMetadata( uk.getFilterName() ) == null ) {
				//						script.add( uk.sqlCreateString(dialect, mapping) );
				//					}
				//				}
			}
		}

		iter = iterateGenerators(dialect);
		while (iter.hasNext()) {
			PersistentIdentifierGenerator generator = (PersistentIdentifierGenerator) iter.next();
			Object key = generator.generatorKey();
			if (!databaseMetadata.isSequence(key) && !databaseMetadata.isTable(key)) {
				String[] lines = generator.sqlCreateStrings(dialect);
				for (int i = 0; i < lines.length; i++) {
					script.add(lines[i]);
				}
			}
		}

		return ArrayHelper.toStringArray(script);
	}

	/**
	 * 
	* @Title: sqlAlterStrings
	* @Description: 修改字段类型Alter   table     表名   Alter   Column   字段名   varchar(50) 
	*添加字段alter   table   table2   add   name   char(8) 
	*删除字段alter   table   table2   drop   column   id
	*修改字段名alter table A rename column a to c
	* @param @param table
	* @param @param dialect
	* @param @param p
	* @param @param tableInfo
	* @param @param defaultCatalog
	* @param @param defaultSchema
	* @param @return
	* @param @throws HibernateException    设定文件
	* @return Iterator    返回类型
	* @throws
	 */
	private Iterator sqlAlterStrings(Table table, Dialect dialect, Mapping p, TableMetadata tableInfo,
			String defaultCatalog, String defaultSchema) throws HibernateException {
		//alter table + tabelname + ' ' + "add" +
		StringBuffer root = new StringBuffer("alter table ").append(
				table.getQualifiedName(dialect, defaultCatalog, defaultSchema)).append(' ');
		//				.append(dialect.getAddColumnString());
		//这里的tableinfo为从数据表中读出的table信息   ，table表信息为config配置的信息
		Iterator iter = table.getColumnIterator();
		List results = new ArrayList();
		while (iter.hasNext()) {
			Column column = (Column) iter.next();

			ColumnMetadata columnInfo = tableInfo.getColumnMetadata(column.getName());

			if (columnInfo == null) {
				// the column doesnt exist at all.
				StringBuffer alter = new StringBuffer(root.toString()).append(dialect.getAddColumnString()).append(' ')
						.append(column.getQuotedName(dialect)).append(' ').append(column.getSqlType(dialect, p));

				String defaultValue = column.getDefaultValue();
				if (defaultValue != null) {
					alter.append(" default ").append(defaultValue);
				}

				if (column.isNullable()) {
					alter.append(dialect.getNullColumnString());
				} else {
					alter.append(" not null");
				}

				boolean useUniqueConstraint = column.isUnique() && dialect.supportsUnique()
						&& (!column.isNullable() || dialect.supportsNotNullUnique());
				if (useUniqueConstraint) {
					alter.append(" unique");
				}

				if (column.hasCheckConstraint() && dialect.supportsColumnCheck()) {
					alter.append(" check(").append(column.getCheckConstraint()).append(")");
				}

				String columnComment = column.getComment();
				if (columnComment != null) {
					alter.append(dialect.getColumnComment(columnComment));
				}
				System.out.println(alter);
				results.add(alter.toString());
			}
			//处理更改字段 判断是否类型更改，为null更改，这里处理还要调整
			//长度的话这里先处理varchar类型
			//!columnInfo.getTypeName().equals(column.getSqlType(dialect, p))
			else if (columnInfo.getTypeCode() != column.getSqlTypeCode(p)
					|| !columnInfo.getNullable().toLowerCase().equals(column.isNullable() ? "yes" : "no")
					|| (columnInfo.getTypeCode() == column.getSqlTypeCode(p) && columnInfo.getTypeCode() == 12 && columnInfo
							.getColumnSize() != column.getLength())) {

				StringBuffer alter = new StringBuffer(root.toString()).append("alter column").append(' ')
						.append(column.getQuotedName(dialect)).append(' ').append(column.getSqlType(dialect, p));

				String defaultValue = column.getDefaultValue();
				if (defaultValue != null) {
					alter.append(" default ").append(defaultValue);
				}

				if (column.isNullable()) {
					alter.append(dialect.getNullColumnString());
				} else {
					alter.append(" not null");
				}

				boolean useUniqueConstraint = column.isUnique() && dialect.supportsUnique()
						&& (!column.isNullable() || dialect.supportsNotNullUnique());
				if (useUniqueConstraint) {
					alter.append(" unique");
				}

				if (column.hasCheckConstraint() && dialect.supportsColumnCheck()) {
					alter.append(" check(").append(column.getCheckConstraint()).append(")");
				}

				String columnComment = column.getComment();
				if (columnComment != null) {
					alter.append(dialect.getColumnComment(columnComment));
				}
				System.out.println(alter);
				results.add(alter.toString());
			}

		}
		//利用反射机制 获取 tableinfo中columns 为hashmap
		//处理字段删除，不存在的表单进行删除操作，这里还要对外键操作进行处理
		Field field;
		Map columnsmap = new HashMap();
		try {
			//tableinfo对应数据库中的表，table对应config配置的表
			field = tableInfo.getClass().getDeclaredField("columns");
			field.setAccessible(true);
			columnsmap = (Map) (field.get(tableInfo));
			List columnnamelist = new ArrayList();
			for (Iterator columnite = table.getColumnIterator(); columnite.hasNext();) {
				Column col = (Column) columnite.next();
				columnnamelist.add(col.getName().toLowerCase());
			}
			for (Object columnname : columnsmap.keySet()) {
				//如果不包含配置文件中的字段
				if (!columnnamelist.contains(columnname.toString().toLowerCase())) {
					//判断是否该column是否为外键
					ColumnMetadata columnmeta = tableInfo.getColumnMetadata(columnname.toString());
					//利用反射获取tableinfo中的foreignkey
					field = tableInfo.getClass().getDeclaredField("foreignKeys");
					field.setAccessible(true);
					Map foreignkeymap = (Map) (field.get(tableInfo));
					Iterator foreignkeymapit = foreignkeymap.values().iterator();
					while (foreignkeymapit.hasNext()) {
						ForeignKeyMetadata existingFk = (ForeignKeyMetadata) foreignkeymapit.next();
						//利用反射获取外键值
						field = existingFk.getClass().getDeclaredField("references");
						field.setAccessible(true);
						Map fkcolumnmap = (Map) (field.get(existingFk));
						if (fkcolumnmap.containsKey(columnname)) {
							StringBuffer alter = new StringBuffer(root.toString())
									.append(dialect.getDropForeignKeyString()).append(' ').append(existingFk.getName());
							System.out.println(alter);
							results.add(alter.toString());

						}

					}

					//					Iterator subIter = tableInfo.getForeignKeyIterator();
					//					while (subIter.hasNext()) {
					//						ForeignKey fk = (ForeignKey) subIter.next();
					//						if (fk.isPhysicalConstraint()) {
					//							boolean create = tableInfo == null || (tableInfo.getForeignKeyMetadata(fk) == null && (
					//							//Icky workaround for MySQL bug:
					//									!(dialect instanceof MySQLDialect) || tableInfo.getIndexMetadata(fk.getName()) == null));
					//							if (create) {
					//								script.add(fk.sqlCreateString(dialect, config.getMapping(), defaultCatalog,
					//										defaultSchema));
					//							}
					//						}
					//					}
					StringBuffer alter = new StringBuffer(root.toString()).append("drop column").append(' ')
							.append(columnname);
					System.out.println(alter);
					results.add(alter.toString());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		//		Iterator iter = tableInfo..getColumnIterator();
		//		if(table.getColumnIterator().)

		return results.iterator();
	}

	private Iterator iterateGenerators(Dialect dialect) throws MappingException {

		TreeMap generators = new TreeMap();
		String defaultCatalog = config.getProperty(Environment.DEFAULT_CATALOG);
		String defaultSchema = config.getProperty(Environment.DEFAULT_SCHEMA);

		Iterator iter = config.getClassMappings();
		while (iter.hasNext()) {
			PersistentClass pc = (PersistentClass) iter.next();
			if (!pc.isInherited()) {
				IdentifierGenerator ig = pc.getIdentifier().createIdentifierGenerator(
						config.getIdentifierGeneratorFactory(), dialect, defaultCatalog, defaultSchema, (RootClass) pc);

				if (ig instanceof PersistentIdentifierGenerator) {
					generators.put(((PersistentIdentifierGenerator) ig).generatorKey(), ig);
				} else if (ig instanceof IdentifierGeneratorAggregator) {
					((IdentifierGeneratorAggregator) ig).registerPersistentGenerators(generators);
				}
			}
		}

		iter = config.getCollectionMappings();
		while (iter.hasNext()) {
			Collection collection = (Collection) iter.next();
			if (collection.isIdentified()) {
				IdentifierGenerator ig = ((IdentifierCollection) collection).getIdentifier().createIdentifierGenerator(
						config.getIdentifierGeneratorFactory(), dialect, defaultCatalog, defaultSchema, null);

				if (ig instanceof PersistentIdentifierGenerator) {
					generators.put(((PersistentIdentifierGenerator) ig).generatorKey(), ig);
				}
			}
		}

		return generators.values().iterator();
	}

	//	private String sqlUpdateColumnNameString(Table table, Dialect dialect, Mapping p, String defaultCatalog,
	//			String defaultSchema) {
	//		return "";
	//	}
	//
	//	private String sqlUpdateColumnTypeString() {
	//		return "";
	//	}
	//
	//	private String sqlDeleteColumnString() {
	//		return "";
	//	}
	//	private String[] generateSchemaUpdateScript(Dialect dialect, DatabaseMetadata databaseMetadata)

}
