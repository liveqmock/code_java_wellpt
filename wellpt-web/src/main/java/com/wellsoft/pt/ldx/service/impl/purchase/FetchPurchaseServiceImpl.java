package com.wellsoft.pt.ldx.service.impl.purchase;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wellsoft.pt.basicdata.sap.config.SAPDbConfig;
import com.wellsoft.pt.ldx.model.purchase.Purchasedata1;
import com.wellsoft.pt.ldx.service.impl.SrmServiceImpl;
import com.wellsoft.pt.ldx.service.purchase.FetchPurchaseService;
import com.wellsoft.pt.ldx.util.DateUtils;

@Service
@Transactional
public class FetchPurchaseServiceImpl extends SrmServiceImpl implements
		FetchPurchaseService {
	@Resource(name="sapConnectConfig")
	private SAPDbConfig sapConfig;

	public FetchPurchaseServiceImpl() {
	}

	@Override
	public void fetchPurchaseData1() throws Exception {
		execSql("delete from purchasedata1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = DateUtils.addMonth(
				DateUtils.getFirstDayOfCurrentMonth(), 1);
		Date toDate = DateUtils.addMonth(fromDate, 3);
		Map<String, String> weekm = new HashMap<String, String>();
		setWeekm(fromDate, DateUtils.addMonth(fromDate, 1), 1, weekm, sdf);
		setWeekm(DateUtils.addMonth(fromDate, 1),
				DateUtils.addMonth(fromDate, 2), 5, weekm, sdf);
		setWeekm(DateUtils.addMonth(fromDate, 2),
				DateUtils.addMonth(fromDate, 3), 9, weekm, sdf);
		String stpSql = "select s.item_code,s.vendor_code,s.quantity,s.factory_code,s.plan_date,z.zjfl2,z.zjfl3 from STP_PRODUCT_PLAN s left join ZMMT0041 z on z.matkl=substr(s.item_code,0,5) and z.mandt='"
				+ sapConfig.getClient()
				+ "' where s.last_version_flag='Y' AND s.enabled_flag='Y'";
		stpSql += " and to_Char(s.plan_date,'yyyy-MM-dd')>='"
				+ sdf.format(fromDate)
				+ "' and to_Char(s.plan_date,'yyyy-MM-dd')<'"
				+ sdf.format(toDate) + "'";
		String lineSql = "select l.QUANTITY,z.zjfl2,z.zjfl3,l.unit_price,la.NEED_BY_DATE from PUR_LINES_ALL l INNER JOIN PUR_headerS_ALL h ON l.pur_header_id = h.pur_header_id AND h.last_version  ='Y' INNER JOIN PUR_LINE_LOCATIONS_ALL la ON l.pur_line_id  = la.pur_line_id AND l.pur_header_id=la.pur_header_id left join ZMMT0041 z on z.matkl=substr(l.item_code,0,5) and z.mandt='"
				+ sapConfig.getClient() + "'";
		lineSql += " where to_Char(la.NEED_BY_DATE,'yyyy-MM-dd')>='"
				+ sdf.format(fromDate)
				+ "' and to_Char(la.NEED_BY_DATE,'yyyy-MM-dd')<'"
				+ sdf.format(toDate) + "'";
		List<Object> os = findListBySql(stpSql);
		Map<String, Map<String, Purchasedata1>> items = new HashMap<String, Map<String, Purchasedata1>>();
		for (Object object : os) {
			setStpData(object, items, weekm, sdf);
		}
		List<Object> list = findListBySql(lineSql);
		for (Object object : list) {
			setLineData(object, items, weekm, sdf);
		}
		Integer number = 1;
		for (String itemKey : items.keySet()) {
			Map<String, Purchasedata1> dataMap = items.get(itemKey);
			for (String dataKey : dataMap.keySet()) {
				Purchasedata1 purchasedata = dataMap.get(dataKey);
				purchasedata.setId(number++);
				purchasedata.count();
				srmDao.save(purchasedata);
			}
		}

	}

	private void setLineData(Object object,
			Map<String, Map<String, Purchasedata1>> items,
			Map<String, String> weekm, SimpleDateFormat sdf) throws Exception {
		if (object instanceof Object[]) {
			String zjfl2 = " ", zjfl3 = " ";
			if (null != ((Object[]) object)[1]) {
				zjfl2 = ((Object[]) object)[1].toString().trim();
			}
			if (null != ((Object[]) object)[2]) {
				zjfl3 = ((Object[]) object)[2].toString().trim();
			}
			if (!items.containsKey(zjfl2)) {
				Map<String, Purchasedata1> datam = new HashMap<String, Purchasedata1>();
				Purchasedata1 p = new Purchasedata1();
				p.setZjfl2(zjfl2);
				p.setZjfl3(zjfl3);
				datam.put(zjfl3, p);
				items.put(zjfl2, datam);
			} else {
				Map<String, Purchasedata1> datam = items.get(zjfl2);
				if (!datam.containsKey(zjfl3)) {
					Purchasedata1 p = new Purchasedata1();
					p.setZjfl2(zjfl2);
					p.setZjfl3(zjfl3);
					datam.put(zjfl3, p);
				}
			}
			BigDecimal quantity = (BigDecimal) ((Object[]) object)[0];
			BigDecimal unit_price = (BigDecimal) ((Object[]) object)[3];
			Date needDate = (Date) ((Object[]) object)[4];
			Purchasedata1 data = items.get(zjfl2).get(zjfl3);
			String week = weekm.get(sdf.format(needDate));
			BigDecimal q = (BigDecimal) PropertyUtils.getProperty(data, "q"
					+ week.replaceAll("w", ""));
			BigDecimal p = (BigDecimal) PropertyUtils.getProperty(data, "p"
					+ week.replaceAll("w", ""));
			q = BigDecimal.valueOf(Double.valueOf(q.toString())
					+ Double.valueOf(quantity.toString()));
			DecimalFormat df = new DecimalFormat("#.00");
			p = new BigDecimal(df.format(Double.valueOf(p.toString())
					+ Double.valueOf(unit_price.toString())));
			PropertyUtils.setProperty(data, "q" + week.replaceAll("w", ""), q);
			PropertyUtils.setProperty(data, "p" + week.replaceAll("w", ""), p);
		}
	}

	private void setStpData(Object object,
			Map<String, Map<String, Purchasedata1>> items,
			Map<String, String> weekm, SimpleDateFormat sdf) throws Exception {
		if (object instanceof Object[]) {
			String zjfl2 = "", zjfl3 = "";
			try {
				zjfl2 = ((Object[]) object)[5].toString().trim();
				zjfl3 = ((Object[]) object)[6].toString().trim();
			} catch (Exception e) {
			}
			if (!items.containsKey(zjfl2)) {
				Map<String, Purchasedata1> datam = new HashMap<String, Purchasedata1>();
				Purchasedata1 p = new Purchasedata1();
				p.setZjfl2(zjfl2);
				p.setZjfl3(zjfl3);
				datam.put(zjfl3, p);
				items.put(zjfl2, datam);
			} else {
				Map<String, Purchasedata1> datam = items.get(zjfl2);
				if (!datam.containsKey(zjfl3)) {
					Purchasedata1 p = new Purchasedata1();
					p.setZjfl2(zjfl2);
					p.setZjfl3(zjfl3);
					datam.put(zjfl3, p);
				}
			}
			String item_code = StringUtils.leftPad(((Object[]) object)[0]
					.toString().trim(), 18, '0');
			String vendor_code = StringUtils.leftPad(((Object[]) object)[1]
					.toString().trim(), 10, '0');
			String factory_code = ((Object[]) object)[3].toString().trim();
			Date planDate = (Date) ((Object[]) object)[4];
			BigDecimal quantity = (BigDecimal) ((Object[]) object)[2];
			BigDecimal cost = new BigDecimal("0.00");
			cost = getCost(item_code, vendor_code, factory_code, quantity);
			Purchasedata1 data = items.get(zjfl2).get(zjfl3);
			String week = weekm.get(sdf.format(planDate));
			BigDecimal q = (BigDecimal) PropertyUtils.getProperty(data, "q"
					+ week.replaceAll("w", ""));
			BigDecimal p = (BigDecimal) PropertyUtils.getProperty(data, "p"
					+ week.replaceAll("w", ""));
			q = BigDecimal.valueOf(Double.valueOf(q.toString())
					+ Double.valueOf(quantity.toString()));
			DecimalFormat df = new DecimalFormat("#.00");
			p = new BigDecimal(df.format(Double.valueOf(p.toString())
					+ Double.valueOf(cost.toString())));
			PropertyUtils.setProperty(data, "q" + week.replaceAll("w", ""), q);
			PropertyUtils.setProperty(data, "p" + week.replaceAll("w", ""), p);
		}
	}

	private BigDecimal getCost(String item_code, String vendor_code,
			String factory_code, BigDecimal quantity) {
		BigDecimal cost = new BigDecimal("0.00");
		List<Object> os = findListBySql("select z.zkbetr,z.matnr from ZMMT0016 z where z.mandt='"
				+ sapConfig.getClient()
				+ "' and z.matnr='"
				+ item_code
				+ "' and z.lifnr='"
				+ vendor_code
				+ "' and z.werks='"
				+ factory_code + "'");
		if (null != os && os.size() > 0) {
			Object[] obArray = (Object[]) os.get(0);
			String zkbetr = obArray[0].toString().trim();
			Double c = Double.valueOf(quantity.toString())
					* Double.valueOf(zkbetr);
			DecimalFormat df = new DecimalFormat("#.00");
			cost = new BigDecimal(df.format(c));
		} else {
			List<Object> list = findListBySql("select z.zkbetr,z.matnr from ZMMT0016 z where z.mandt='"
					+ sapConfig.getClient()
					+ "' and z.matnr='"
					+ item_code
					+ "' and z.lifnr='" + vendor_code + "'");
			if (null != list && list.size() > 0) {
				Object[] obArray = (Object[]) list.get(0);
				String zkbetr = obArray[0].toString().trim();
				Double c = Double.valueOf(quantity.toString())
						* Double.valueOf(zkbetr);
				DecimalFormat df = new DecimalFormat("#.00");
				cost = new BigDecimal(df.format(c));
			}
		}
		return cost;
	}

	private void setWeekm(Date fromDate, Date addMonth, Integer count,
			Map<String, String> weekm, SimpleDateFormat sdf) {
		Date fDate = fromDate;
		Date tDate = addMonth;
		Integer i = 0;
		while (fDate.before(tDate)) {
			i++;
			if (i >= 0 && i <= 7) {
				weekm.put(sdf.format(fDate), "w" + count);
			} else if (i >= 8 && i <= 14) {
				weekm.put(sdf.format(fDate), "w" + (count + 1));
			} else if (i >= 15 && i <= 21) {
				weekm.put(sdf.format(fDate), "w" + (count + 2));
			} else {
				weekm.put(sdf.format(fDate), "w" + (count + 3));
			}
			fDate = DateUtils.addDate(fDate, 1);
		}
	}
}
