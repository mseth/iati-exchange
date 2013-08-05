/**
 * 
 */
package org.dg.iati.importer.engine;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.dg.iati.api.jaxb.iatiImportRules.TypeCompareAs;
import org.dg.iati.api.jaxb.iatiImportRules.TypeComparisonValues;

/**
 * @author alex
 *
 */
public class TestComparator implements Comparator<Object> {
	
	public static final String DEFAULT_DATE_FORMAT	= "yyyy-MM-dd HH:mm:ss";
	
	private TypeCompareAs compareAs;
	private TypeComparisonValues comparison;
	
	public TestComparator(TypeCompareAs compareAs,
			TypeComparisonValues comparison) {
		super();
		this.compareAs = compareAs;
		this.comparison = comparison;
	}
	
	@Override
	public int compare(Object o1, Object o2) {
		if ( TypeComparisonValues.TRUE.equals(this.comparison) )
			return 0;
		else if ( TypeComparisonValues.FALSE.equals(this.comparison) )
			return -1;
		else if ( TypeComparisonValues.NOT_EMPTY.equals(this.comparison) ) {
			if ( o1 == null || o1.toString().trim().length() == 0 || 
				o2 == null || o2.toString().trim().length() == 0 )
				return -1;
			else
				return 0;
		}
		
		int ret = -1;
		try {
			ret = this.internalCompare(o1, o2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private int internalCompare (Object o1, Object o2) throws ParseException{
		int ret = -1;
		if ( o1 != null && o2 != null) {
			if ( TypeCompareAs.STRING.equals(this.compareAs) ) {
				ret = compareStrings(o1, o2);
			}
			else if ( TypeCompareAs.NUMBER.equals(this.compareAs) ) {
				ret = compareNumbers(o1, o2);
			}
			else if ( TypeCompareAs.DATE.equals(this.compareAs) ) {
				ret = compareDates(o1, o2);
			}
			else 
				return ret;
		}
		
		if ( TypeComparisonValues.EQ.equals(this.comparison) ) {
			return ret;
		}
		else if ( TypeComparisonValues.NEQ.equals(this.comparison) ) {
			if ( ret == 0 )
				return -1;
			else
				return 0;
		}
		else if ( TypeComparisonValues.GT.equals(this.comparison) ) {
			if ( ret > 0 ) {
				return 0;
			}
			else 
				return -1;
		}
		else if ( TypeComparisonValues.LT.equals(this.comparison) ) {
			if ( ret < 0 ) {
				return 0;
			}
			else 
				return -1;
		}
		return -1;
	}
	
	private int compareNumbers(Object o1, Object o2) {
		Double d1	=  Double.parseDouble( o1.toString() );
		Double d2	=  Double.parseDouble( o2.toString() );
		return d1.compareTo(d2);
	}

	private int compareStrings(Object o1, Object o2) {
		String s1	=  o1.toString();
		String s2	=  o2.toString();
		return s1.compareTo(s2);
	}

	private int compareDates(Object o1, Object o2) throws ParseException {
		DateFormat df	= new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Date d1	=  o1 instanceof Date ? (Date)o1 : df.parse( o1.toString() );
		Date d2	=  o2 instanceof Date ? (Date)o2 : df.parse( o2.toString() );
		return d1.compareTo(d2);
	}


}
