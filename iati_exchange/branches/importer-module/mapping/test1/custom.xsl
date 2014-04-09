<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<iati-activities>
			<xsl:for-each select="iati-activities//iati-activity">
				<iati-activity>
					<xsl:apply-templates />
				</iati-activity>
			</xsl:for-each>
		</iati-activities>
	</xsl:template>
	<xsl:template match="item[@ref='iati-identifier']" name="iati-identifier">
		<iati-identifier>
			<xsl:value-of select="./value" />
		</iati-identifier>
	</xsl:template>
	<xsl:template match="item[@ref='title']" name="title">
		<title>
			<xsl:value-of select="./value" />
		</title>
	</xsl:template>
	<xsl:template match="item[@ref='description']" name="description">
		<xsl:variable name="type">
			<xsl:value-of select="./attribute[@ref='type']" />
		</xsl:variable>
		<xsl:variable name="xml:lang">
			<xsl:value-of select="./attribute[@ref='xml:lang']" />
		</xsl:variable>
		<description type="{$type}" xml:lang="{$xml:lang}">
			<xsl:value-of select="./value" />
		</description>
	</xsl:template>
	<xsl:template match="item[@ref='related-activity']" name="related-activity">
		<xsl:variable name="xml:lang">
			<xsl:value-of select="./attribute[@ref='xml:lang']" />
		</xsl:variable>
		<related-activity xml:lang="{$xml:lang}">
			<xsl:value-of select="./value" />
		</related-activity>
	</xsl:template>
	<xsl:template match="item[@ref='activity-status']" name="activity-status">
		<xsl:variable name="xml:lang">
			<xsl:value-of select="./attribute[@ref='xml:lang']" />
		</xsl:variable>
		<activity-status xml:lang="{$xml:lang}">
			<xsl:value-of select="./value" />
		</activity-status>
	</xsl:template>
	<xsl:template match="item[@ref='activity-date']" name="activity-date">
		<xsl:variable name="type">
			<xsl:value-of select="./attribute[@ref='type']" />
		</xsl:variable>
		<xsl:variable name="iso-date">
			<xsl:value-of select="./attribute[@ref='iso-date']" />
		</xsl:variable>
		<activity-date type="{$type}" iso-date="{$iso-date}">
			<xsl:value-of select="./value" />
		</activity-date>
	</xsl:template>
	<xsl:template match="item[@ref='location-type']" name="location.location-type">
		<xsl:variable name="code">
			<xsl:value-of select="./item[@ref='location-type']/attribute[@ref='code']" />
		</xsl:variable>
		<location-type code="{$code}">
			<xsl:value-of select="./item[@ref='location-type']/value" />
		</location-type>
	</xsl:template>
	<xsl:template match="item[@ref='name']" name="location.name">
		<name>
			<xsl:value-of select="./item[@ref='name']/value" />
		</name>
	</xsl:template>
	<xsl:template match="item[@ref='location']" name="location">
		<xsl:variable name="percentage">
			<xsl:value-of select="./attribute[@ref='percentage']" />
		</xsl:variable>
		<location percentage="{$percentage}">
			<xsl:call-template name="location.location-type"></xsl:call-template>
			<xsl:call-template name="location.name"></xsl:call-template>
		</location>
	</xsl:template>
	<xsl:template match="item[@ref='sector']" name="sector">
		<xsl:variable name="code">
			<xsl:value-of select="./attribute[@ref='code']" />
		</xsl:variable>
		<xsl:variable name="vocabulary">
			<xsl:value-of select="./attribute[@ref='vocabulary']" />
		</xsl:variable>
		<xsl:variable name="percentage">
			<xsl:value-of select="./attribute[@ref='percentage']" />
		</xsl:variable>
		<sector code="{$code}" vocabulary="{$vocabulary}" percentage="{$percentage}">
			<xsl:value-of select="./value" />
		</sector>
	</xsl:template>
	<xsl:template match="item[@ref='participating-org']" name="participating-org">
		<xsl:variable name="role">
			<xsl:value-of select="./attribute[@ref='role']" />
		</xsl:variable>
		<participating-org role="{$role}">
			<xsl:value-of select="./value" />
		</participating-org>
	</xsl:template>
	<xsl:template match="item[@ref='transaction-type']" name="transaction.transaction-type">
		<xsl:variable name="code">
			<xsl:value-of
				select="./item[@ref='transaction-type']/attribute[@ref='code']" />
		</xsl:variable>
		<transaction-type code="{$code}">
			<xsl:value-of select="./item[@ref='transaction-type']/value" />
		</transaction-type>
	</xsl:template>
	<xsl:template match="item[@ref='provider-org']" name="transaction.provider-org">
		<xsl:variable name="provider-activity-id">
			<xsl:value-of
				select="./item[@ref='provider-org']/attribute[@ref='provider-activity-id']" />
		</xsl:variable>
		<provider-org provider-activity-id="{$provider-activity-id}">
			<xsl:value-of select="./item[@ref='provider-org']/value" />
		</provider-org>
	</xsl:template>
	<xsl:template match="item[@ref='value']" name="transaction.value">
		<xsl:variable name="currency">
			<xsl:value-of select="./item[@ref='value']/attribute[@ref='currency']" />
		</xsl:variable>
		<xsl:variable name="value-date">
			<xsl:value-of select="./item[@ref='value']/attribute[@ref='value-date']" />
		</xsl:variable>
		<value currency="{$currency}" value-date="{$value-date}">
			<xsl:value-of select="./item[@ref='value']/value" />
		</value>
	</xsl:template>
	<xsl:template match="item[@ref='finance-type']" name="transaction.finance-type">
		<xsl:variable name="code">
			<xsl:value-of select="./item[@ref='finance-type']/attribute[@ref='code']" />
		</xsl:variable>
		<finance-type code="{$code}">
			<xsl:value-of select="./item[@ref='finance-type']/value" />
		</finance-type>
	</xsl:template>
	<xsl:template match="item[@ref='aid-type']" name="transaction.aid-type">
		<aid-type>
			<xsl:value-of select="./item[@ref='aid-type']/value" />
		</aid-type>
	</xsl:template>
	<xsl:template match="item[@ref='transaction']" name="transaction">
		<transaction>
			<xsl:call-template name="transaction.transaction-type"></xsl:call-template>
			<xsl:call-template name="transaction.provider-org"></xsl:call-template>
			<xsl:call-template name="transaction.value"></xsl:call-template>
			<xsl:call-template name="transaction.finance-type"></xsl:call-template>
			<xsl:call-template name="transaction.aid-type"></xsl:call-template>
		</transaction>
	</xsl:template>
	<xsl:template match="item[@ref='provider-org']" name="planned-disbursement.provider-org">
		<xsl:variable name="provider-activity-id">
			<xsl:value-of
				select="./item[@ref='provider-org']/attribute[@ref='provider-activity-id']" />
		</xsl:variable>
		<provider-org provider-activity-id="{$provider-activity-id}">
			<xsl:value-of select="./item[@ref='provider-org']/value" />
		</provider-org>
	</xsl:template>
	<xsl:template match="item[@ref='value']" name="planned-disbursement.value">
		<xsl:variable name="currency">
			<xsl:value-of select="./item[@ref='value']/attribute[@ref='currency']" />
		</xsl:variable>
		<xsl:variable name="value-date">
			<xsl:value-of select="./item[@ref='value']/attribute[@ref='value-date']" />
		</xsl:variable>
		<value currency="{$currency}" value-date="{$value-date}">
			<xsl:value-of select="./item[@ref='value']/value" />
		</value>
	</xsl:template>
	<xsl:template match="item[@ref='finance-type']" name="planned-disbursement.finance-type">
		<finance-type>
			<xsl:value-of select="./item[@ref='finance-type']/value" />
		</finance-type>
	</xsl:template>
	<xsl:template match="item[@ref='aid-type']" name="planned-disbursement.aid-type">
		<aid-type>
			<xsl:value-of select="./item[@ref='aid-type']/value" />
		</aid-type>
	</xsl:template>
	<xsl:template match="item[@ref='planned-disbursement']"
		name="planned-disbursement">
		<planned-disbursement>
			<xsl:call-template name="planned-disbursement.provider-org"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.value"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.finance-type"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.aid-type"></xsl:call-template>
		</planned-disbursement>
	</xsl:template>
	<xsl:template match="item[@ref='provider-org']" name="planned-commitment.provider-org">
		<xsl:variable name="provider-activity-id">
			<xsl:value-of
				select="./item[@ref='provider-org']/attribute[@ref='provider-activity-id']" />
		</xsl:variable>
		<provider-org provider-activity-id="{$provider-activity-id}">
			<xsl:value-of select="./item[@ref='provider-org']/value" />
		</provider-org>
	</xsl:template>
	<xsl:template match="item[@ref='value']" name="planned-commitment.value">
		<xsl:variable name="currency">
			<xsl:value-of select="./item[@ref='value']/attribute[@ref='currency']" />
		</xsl:variable>
		<xsl:variable name="value-date">
			<xsl:value-of select="./item[@ref='value']/attribute[@ref='value-date']" />
		</xsl:variable>
		<value currency="{$currency}" value-date="{$value-date}">
			<xsl:value-of select="./item[@ref='value']/value" />
		</value>
	</xsl:template>
	<xsl:template match="item[@ref='finance-type']" name="planned-commitment.finance-type">
		<finance-type>
			<xsl:value-of select="./item[@ref='finance-type']/value" />
		</finance-type>
	</xsl:template>
	<xsl:template match="item[@ref='aid-type']" name="planned-commitment.aid-type">
		<aid-type>
			<xsl:value-of select="./item[@ref='aid-type']/value" />
		</aid-type>
	</xsl:template>
	<xsl:template match="item[@ref='planned-commitment']"
		name="planned-commitment">
		<planned-commitment>
			<xsl:call-template name="planned-commitment.provider-org"></xsl:call-template>
			<xsl:call-template name="planned-commitment.value"></xsl:call-template>
			<xsl:call-template name="planned-commitment.finance-type"></xsl:call-template>
			<xsl:call-template name="planned-commitment.aid-type"></xsl:call-template>
		</planned-commitment>
	</xsl:template>
</xsl:stylesheet>