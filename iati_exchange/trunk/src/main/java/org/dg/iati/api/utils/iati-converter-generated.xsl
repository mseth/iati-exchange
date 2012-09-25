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
		<xsl:variable name="xml:lang">
			<xsl:value-of select="./attribute[@ref='xml:lang']" />
		</xsl:variable>
		<title xml:lang="{$xml:lang}">
			<xsl:value-of select="./value" />
		</title>
	</xsl:template>
	<xsl:template match="item[@ref='description']" name="description">
		<xsl:variable name="owner-ref">
			<xsl:value-of select="./attribute[@ref='owner-ref']" />
		</xsl:variable>
		<xsl:variable name="owner-name">
			<xsl:value-of select="./attribute[@ref='owner-name']" />
		</xsl:variable>
		<description owner-ref="{$owner-ref}" owner-name="{$owner-name}">
			<xsl:value-of select="./value" />
		</description>
	</xsl:template>
	<xsl:template match="item[@ref='sector']" name="sector">
		<xsl:variable name="code">
			<xsl:value-of select="./attribute[@ref='code']" />
		</xsl:variable>
		<xsl:variable name="percentage">
			<xsl:value-of select="./attribute[@ref='percentage']" />
		</xsl:variable>
		<xsl:variable name="vocabulary">
			<xsl:value-of select="./attribute[@ref='vocabulary']" />
		</xsl:variable>
		<sector code="{$code}" percentage="{$percentage}" vocabulary="{$vocabulary}">
			<xsl:value-of select="./value" />
		</sector>
	</xsl:template>
	<xsl:template match="item[@ref='transaction-type']" name="transaction.transaction-type">
		<transaction-type>
			<xsl:value-of select="./item[@ref='transaction-type']/value" />
		</transaction-type>
	</xsl:template>
	<xsl:template match="item[@ref='provider-org']" name="transaction.provider-org">
		<provider-org>
			<xsl:value-of select="./item[@ref='provider-org']/value" />
		</provider-org>
	</xsl:template>
	<xsl:template match="item[@ref='value']" name="transaction.value">
		<value>
			<xsl:value-of select="./item[@ref='value']/value" />
		</value>
	</xsl:template>
	<xsl:template match="item[@ref='transaction-date']" name="transaction.transaction-date">
		<transaction-date>
			<xsl:value-of select="./item[@ref='transaction-date']/value" />
		</transaction-date>
	</xsl:template>
	<xsl:template match="item[@ref='transaction']" name="transaction">
		<xsl:variable name="ref">
			<xsl:value-of select="./attribute[@ref='ref']" />
		</xsl:variable>
		<transaction ref="{$ref}">
			<xsl:call-template name="transaction.transaction-type"></xsl:call-template>
			<xsl:call-template name="transaction.provider-org"></xsl:call-template>
			<xsl:call-template name="transaction.value"></xsl:call-template>
			<xsl:call-template name="transaction.transaction-date"></xsl:call-template>
		</transaction>
	</xsl:template>
</xsl:stylesheet>