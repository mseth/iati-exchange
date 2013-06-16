<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<iati-activities>
			<period-start>
				<xsl:value-of
					select="/iati-activities/iati-activity[1]//item[@ref='feed-period-start']/value" />
			</period-start>
				<period-end>
					<xsl:value-of
						select="/iati-activities/iati-activity[1]//item[@ref='feed-period-end']/value" />
				</period-end>
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
	<xsl:template match="item[@ref='other-identifier']" name="other-identifier">
		<xsl:variable name="owner-ref">
			<xsl:value-of select="./attribute[@ref='owner-ref']" />
		</xsl:variable>
		<xsl:variable name="owner-name">
			<xsl:value-of select="./attribute[@ref='owner-name']" />
		</xsl:variable>
		<other-identifier owner-ref="{$owner-ref}"
			owner-name="{$owner-name}">
			<xsl:value-of select="./value" />
		</other-identifier>
	</xsl:template>
	<xsl:template match="item[@ref='title']" name="title">
		<xsl:variable name="xml:lang">
			<xsl:value-of select="./attribute[@ref='xml:lang']" />
		</xsl:variable>
		<title xml:lang="{$xml:lang}">
			<xsl:value-of select="./value" />
		</title>
	</xsl:template>
	<xsl:template match="item[@ref='participating-org']" name="participating-org">
		<xsl:variable name="ref">
			<xsl:value-of select="./attribute[@ref='ref']" />
		</xsl:variable>
		<xsl:variable name="role">
			<xsl:value-of select="./attribute[@ref='role']" />
		</xsl:variable>
		<xsl:variable name="xml:lang">
			<xsl:value-of select="./attribute[@ref='xml:lang']" />
		</xsl:variable>
		<xsl:variable name="budget-ref">
			<xsl:value-of select="./attribute[@ref='budget-ref']" />
		</xsl:variable>
		<participating-org ref="{$ref}" role="{$role}"
			xml:lang="{$xml:lang}" budget-ref="{$budget-ref}">
			<xsl:value-of select="./value" />
		</participating-org>
	</xsl:template>
	<xsl:template match="item[@ref='feed-period-start']" name="feed-period-start">
		
	</xsl:template>
	<xsl:template match="item[@ref='feed-period-end']" name="feed-period-end">
		
	</xsl:template>
	<xsl:template match="item[@ref='period-start']" name="planned-disbursement.period-start">
		<xsl:variable name="iso-date">
			<xsl:value-of
				select="./item[@ref='period-start']/attribute[@ref='iso-date']" />
		</xsl:variable>
		<period-start iso-date="{$iso-date}">
			<xsl:value-of select="./item[@ref='period-start']/value" />
		</period-start>
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
	<xsl:template match="item[@ref='provider-org']" name="planned-disbursement.provider-org">
		<xsl:variable name="ref">
			<xsl:value-of select="./item[@ref='provider-org']/attribute[@ref='ref']" />
		</xsl:variable>
		<xsl:variable name="budget-ref">
			<xsl:value-of
				select="./item[@ref='provider-org']/attribute[@ref='budget-ref']" />
		</xsl:variable>
		<provider-org ref="{$ref}" budget-ref="{$budget-ref}">
			<xsl:value-of select="./item[@ref='provider-org']/value" />
		</provider-org>
	</xsl:template>
	<xsl:template match="item[@ref='receiver-org']" name="planned-disbursement.receiver-org">
		<xsl:variable name="ref">
			<xsl:value-of select="./item[@ref='receiver-org']/attribute[@ref='ref']" />
		</xsl:variable>
		<xsl:variable name="budget-ref">
			<xsl:value-of
				select="./item[@ref='receiver-org']/attribute[@ref='budget-ref']" />
		</xsl:variable>
		<receiver-org ref="{$ref}" budget-ref="{$budget-ref}">
			<xsl:value-of select="./item[@ref='receiver-org']/value" />
		</receiver-org>
	</xsl:template>
	<xsl:template match="item[@ref='source-type']" name="planned-disbursement.source-type">
		<xsl:variable name="code">
			<xsl:value-of select="./item[@ref='source-type']/attribute[@ref='code']" />
		</xsl:variable>
		<source-type code="{$code}">
			<xsl:value-of select="./item[@ref='source-type']/value" />
		</source-type>
	</xsl:template>
	<xsl:template match="item[@ref='receiver-activity-id']"
		name="planned-disbursement.receiver-activity-id">
		<receiver-activity-id>
			<xsl:value-of select="./item[@ref='receiver-activity-id']/value" />
		</receiver-activity-id>
	</xsl:template>
	<xsl:template match="item[@ref='planned-disbursement']"
		name="planned-disbursement">
		<planned-disbursement>
			<xsl:call-template name="planned-disbursement.period-start"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.value"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.provider-org"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.receiver-org"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.source-type"></xsl:call-template>
			<xsl:call-template name="planned-disbursement.receiver-activity-id"></xsl:call-template>
		</planned-disbursement>
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
		<xsl:variable name="ref">
			<xsl:value-of select="./item[@ref='provider-org']/attribute[@ref='ref']" />
		</xsl:variable>
		<xsl:variable name="budget-ref">
			<xsl:value-of
				select="./item[@ref='provider-org']/attribute[@ref='budget-ref']" />
		</xsl:variable>
		<provider-org ref="{$ref}" budget-ref="{$budget-ref}">
			<xsl:value-of select="./item[@ref='provider-org']/value" />
		</provider-org>
	</xsl:template>
	<xsl:template match="item[@ref='receiver-org']" name="transaction.receiver-org">
		<xsl:variable name="ref">
			<xsl:value-of select="./item[@ref='receiver-org']/attribute[@ref='ref']" />
		</xsl:variable>
		<xsl:variable name="budget-ref">
			<xsl:value-of
				select="./item[@ref='receiver-org']/attribute[@ref='budget-ref']" />
		</xsl:variable>
		<receiver-org ref="{$ref}" budget-ref="{$budget-ref}">
			<xsl:value-of select="./item[@ref='receiver-org']/value" />
		</receiver-org>
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
	<xsl:template match="item[@ref='flow-type']" name="transaction.flow-type">
		<xsl:variable name="code">
			<xsl:value-of select="./item[@ref='flow-type']/attribute[@ref='code']" />
		</xsl:variable>
		<flow-type code="{$code}">
			<xsl:value-of select="./item[@ref='flow-type']/value" />
		</flow-type>
	</xsl:template>
	<xsl:template match="item[@ref='source-type']" name="transaction.source-type">
		<xsl:variable name="code">
			<xsl:value-of select="./item[@ref='source-type']/attribute[@ref='code']" />
		</xsl:variable>
		<source-type code="{$code}">
			<xsl:value-of select="./item[@ref='source-type']/value" />
		</source-type>
	</xsl:template>
	<xsl:template match="item[@ref='receiver-activity-id']"
		name="transaction.receiver-activity-id">
		<receiver-activity-id>
			<xsl:value-of select="./item[@ref='receiver-activity-id']/value" />
		</receiver-activity-id>
	</xsl:template>
	<xsl:template match="item[@ref='transaction']" name="transaction">
		<transaction>
			<xsl:call-template name="transaction.transaction-type"></xsl:call-template>
			<xsl:call-template name="transaction.provider-org"></xsl:call-template>
			<xsl:call-template name="transaction.receiver-org"></xsl:call-template>
			<xsl:call-template name="transaction.value"></xsl:call-template>
			<xsl:call-template name="transaction.flow-type"></xsl:call-template>
			<xsl:call-template name="transaction.source-type"></xsl:call-template>
			<xsl:call-template name="transaction.receiver-activity-id"></xsl:call-template>
		</transaction>
	</xsl:template>
</xsl:stylesheet>