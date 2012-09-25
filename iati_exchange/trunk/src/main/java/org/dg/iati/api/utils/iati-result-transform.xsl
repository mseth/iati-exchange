<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml" indent="yes"/>
<xsl:template match="/">
<iati-activities>
    <xsl:for-each select="iati-activities//iati-activity">
      <iati-activity>
		<xsl:apply-templates/>
      </iati-activity>
    </xsl:for-each>
</iati-activities>
</xsl:template>
      	<xsl:template match="item[@ref='title']">
      		<xsl:variable name="lang" ><xsl:value-of select="./attribute[@ref='xml:lang']" /></xsl:variable>
      		<title xml:lang="{$lang}">
		    	<xsl:value-of select="./value"/>
		    </title>
      	</xsl:template>
      	<xsl:template match="item[@ref='iati-identifier']">
		    <iati-identifier >
		    	<xsl:value-of select="./value"/>
		    </iati-identifier>
      	</xsl:template>
      	<xsl:template match="item[@ref='description']">
      		<xsl:variable name="owner-ref" ><xsl:value-of select="./attribute[@ref='owner-ref']"/></xsl:variable>
      		<xsl:variable name="owner-name" ><xsl:value-of select="./attribute[@ref='owner-name']"/></xsl:variable>
      		<description owner-ref="{$owner-ref}" owner-name="{$owner-name}">
      			<xsl:value-of select="./value"/>
			</description>
      	</xsl:template>
      	<xsl:template match="item[@ref='sector']" name="sector">
      		<xsl:variable name="code"><xsl:value-of select="./attribute[@ref='code']"/></xsl:variable>
      		<xsl:variable name="vocabulary"><xsl:value-of select="./attribute[@ref='vocabulary']"/></xsl:variable>
      		<xsl:variable name="percentage"><xsl:value-of select="./attribute[@ref='percentage']"/></xsl:variable>
      		<sector code="{$code}" vocabulary="{$vocabulary}" percentage="{$percentage}" ><xsl:value-of select="./value"/></sector>
      	</xsl:template>
      	<xsl:template match="item[@ref='transaction']">
      		<xsl:variable name="ref"><xsl:value-of select="./attribute[@ref='ref']"/></xsl:variable>
      		<transaction ref="{$ref}">
      			<xsl:call-template name="transaction-type"></xsl:call-template>
      		</transaction>
      	</xsl:template>
		<xsl:template match="item[@ref='transaction-type']" name="transaction-type">
    		<xsl:variable name="code"><xsl:value-of select="./attribute[@ref='ref']"/></xsl:variable>
    		<transaction-type code="{$code}"  ><xsl:value-of select="./value"/>
    		</transaction-type>
      	</xsl:template>
</xsl:stylesheet> 


