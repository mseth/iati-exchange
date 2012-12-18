/**
 * 
 */
package org.dg.iati.api.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dg.iati.api.jaxb.iatiApiMapping.AttributeType;
import org.dg.iati.api.jaxb.iatiApiMapping.ContentType;
import org.dg.iati.api.jaxb.iatiApiMapping.Field;
import org.dg.iati.api.jaxb.iatiApiResult.Item;
import org.dg.iati.api.jaxb.iatiApiResult.ObjectFactory;
import org.dg.iati.api.jaxb.iatiApiResult.RefType;
import org.dg.iati.api.util.IatiUtils;

/**
 * @author dan
 * 
 */
public class IatiMappingFieldWorker {

	private Field field = null;
	private String iatiLabel = null;
	private ResultSet globalRS = null;
	private Connection con = null;
	private String parentID = null;

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ResultSet getGlobalRS() {
		return globalRS;
	}

	public void setGlobalRS(ResultSet globalRS) {
		this.globalRS = globalRS;
	}

	// false = column|constant
	// true = select
	private boolean select = false;
	private IatiMappedValue iatiMappedValue;

	public String getIatiLabel() {
		return iatiLabel;
	}

	public void setIatiLabel(String iatiLabel) {
		this.iatiLabel = iatiLabel;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public IatiMappingFieldWorker(Field field) {
		super();
		this.field = field;
		this.setIatiLabel(this.getField().getRef());
		if (this.getField().getQuery() != null
				&& "".compareTo(this.getField().getQuery().getContent()) != 0)
			this.select = true;
		if (this.getField().getContent()!=null 
				&&	this.getField().getContent().getType() != null
				&& "select".compareToIgnoreCase(this.getField().getContent()
						.getType()) == 0)
			this.select = true;
	}

	public Field getField() {
		return field;
	}

	public Field isComplexField() {
		if (field.getField() != null && field.getField().size() > 0) {
			for (Iterator<Field> it = field.getField().iterator(); it.hasNext();) {
				Field f = (Field) it.next();
				if (f.getRef().contains(".content"))
					return f;
			}
		}
		return null;
	}

	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * 
	 */
	public IatiMappingFieldWorker() {
		// TODO Auto-generated constructor stub
	}

	public IatiMappingFieldWorker(Field field, ResultSet globalRS) {
		// TODO Auto-generated constructor stub
		this(field);
		this.globalRS = globalRS;
	}

	public IatiMappingFieldWorker(Field field, ResultSet globalRS,
			Connection con) {
		// TODO Auto-generated constructor stub
		this(field, globalRS);
		this.con = con;
	}

	public IatiMappingFieldWorker(Field field, ResultSet globalRS,
			Connection con, String currentActivityID,
			IatiMappedValue iatiMappedValue) {
		// TODO Auto-generated constructor stub
		this(field, globalRS, con);
		this.iatiMappedValue = iatiMappedValue;
		this.parentID = currentActivityID;
	}

	/**
	 * @throws SQLException
	 */

	public void printContent() throws SQLException {
		// if(!select)
		globalRS.first();
		System.out.println("<item ref=\"" + this.getIatiLabel() + "\">");
		System.out.println("<value>" + this.getField().getContent().getPrefix()
				+ this.getField().getContent().getContent() + "</value>");
		System.out.println("</item>");
	}

	public String getContentItem() {
		return this.getField().getContent().getContent();
	}

	public ContentType getContent() {
		return this.getField().getContent();
	}

	public String getPrefix() {
		return this.getField().getContent().getPrefix();
	}

	public String getQuery() {
		return this.getField().getQuery().getContent();
	}

	public ArrayList<Item> getResultItemList() throws SQLException {
		ResultSet rs = null;
		Field complexField = isComplexField();
		String q = null;

		if (select) {
			if (complexField != null)
				q = IatiUtils.getCleanQuery(complexField.getQuery()
						.getContent(), parentID);
			else
				q = IatiUtils.getCleanQuery(getQuery(), parentID);
			// System.out.println("Running this query ... "+q);
			rs = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(q);
		} else {
			rs = globalRS;
			// System.out.println("Running GLOBAL query ... ");
		}
		rs.beforeFirst();
		ArrayList<Item> result = new ArrayList<Item>();
		while (rs.next()) {
			result.add(createResultItem(rs));
		}

		// itemToXml(resultItem);
		return result;

	}

	/**
	 * 
	 * <field ref="description"> <content type="column">description</content>
	 * <attribute type="constant" ref="owner-ref"
	 * prefix="WB-ER-12-">en</attribute> <attribute type="constant"
	 * ref="owner-name">en</attribute> <field ref=""></field> </field>
	 */
	/**
	 * <item ref="title"> <value>this is the first title of the activity
	 * 1</value> <attribute ref="lang">en</attribute> <attribute
	 * ref="testAttribute">test attribute value</attribute> </item>
	 * 
	 * @param rs
	 * @param iatiMappedValue
	 * @throws SQLException
	 */

	public Item createResultItem(ResultSet rs) throws SQLException {
		ObjectFactory o = new ObjectFactory();
		Item resultItem = o.createItem();
		resultItem.setRef(iatiLabel);
		// globalRS.first();
		String value = null;
		value = getValueFromResultSet(resultItem.getRef(), getContent()
				.getType(), getPrefix(), getContentItem(), rs);
		resultItem.setValue(value);

		List<AttributeType> attributes = getAttributes();
		if (attributes != null && attributes.size() > 0) {
			for (Iterator<AttributeType> it = attributes.iterator(); it
					.hasNext();) {
				AttributeType attr = (AttributeType) it.next();
				// List<RefType> attribute = item.getAttribute();
				RefType attrResult = o.createRefType();
				attrResult.setRef(attr.getRef());
				value = getValueFromResultSet(resultItem.getRef() + ".@"
						+ attrResult.getRef(), attr.getType(),
						attr.getPrefix(), attr.getContent(), rs);
				attrResult.setContent(value);
				resultItem.getAttribute().add(attrResult);
			}
		}

		processSubItemsList(resultItem, rs);

		// itemToXml(resultItem);
		return resultItem;

	}

	private void processSubItemsList(Item parent, ResultSet rs)
			throws SQLException {
		// TODO Auto-generated method stub
		if (this.getSubFieldsList() != null
				&& this.getSubFieldsList().size() > 0) {
			for (Field f : this.getSubFieldsList()) {
				if (!isValidString(f.getRef()) || f.getContent() == null)
					continue;
				ObjectFactory o = new ObjectFactory();
				Item resultItem = o.createItem();
				resultItem.setRef(f.getRef());
				// globalRS.first();
				String value = null;
				try {
					value = getValueFromResultSet(parent.getRef() + "."
							+ resultItem.getRef(), f.getContent().getType(), f
							.getContent().getPrefix(), f.getContent()
							.getContent(), rs);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				resultItem.setValue(value);

				List<AttributeType> attributes = f.getAttribute();
				if (attributes != null && attributes.size() > 0) {
					for (Iterator<AttributeType> it = attributes.iterator(); it
							.hasNext();) {
						AttributeType attr = (AttributeType) it.next();
						// List<RefType> attribute = item.getAttribute();
						RefType attrResult = o.createRefType();
						attrResult.setRef(attr.getRef());
						value = getValueFromResultSet(resultItem.getRef()
								+ ".@" + attrResult.getRef(), attr.getType(),
								attr.getPrefix(), attr.getContent(), rs);
						attrResult.setContent(value);
						resultItem.getAttribute().add(attrResult);
					}
				}
				parent.getItem().add(resultItem);
			}
		}
	}

	private List<Field> getSubFieldsList() {
		// TODO Auto-generated method stub
		return this.getField().getField();
	}

	private boolean isValidString(String s) {
		if (s != null && "".compareTo(s.trim()) != 0)
			return true;
		return false;
	}

	private String getValueFromResultSet(String path, String type,
			String prefix, String content, ResultSet rs) throws SQLException {
		String result = "";
		if (Constants.CONTENT_TYPE_CONSTANT.compareTo(type) == 0) {
			result = (prefix != null ? prefix : "")
					+ (content != null ? content : "");
		}
		if (Constants.CONTENT_TYPE_COLUMN.compareTo(type) == 0) {
			String aux = rs.getString(content);
			result = (prefix != null ? prefix : "") + (aux != null ? aux : "");
		}
		if(this.getIatiMappedValue().hasMapping())
			return this.getIatiMappedValue().getValue(path, result);
		else 
			return result;
	}

	private List<AttributeType> getAttributes() {
		return this.getField().getAttribute();
	}

	public int countRows(ResultSet rs) throws SQLException {
		rs.afterLast();
		int size = rs.getRow() - 1;
		rs.first();
		return size;
	}

	public int countGlobalRSrows() throws SQLException {
		return countRows(globalRS);
	}

	public IatiMappedValue getIatiMappedValue() {
		return iatiMappedValue;
	}

	public void setIatiMappedValue(IatiMappedValue iatiMappedValue) {
		this.iatiMappedValue = iatiMappedValue;
	}

}

