<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" media-type="text/xml" encoding="iso-8859-1" indent="yes" />
	<xsl:template match="/">
		<xsl:variable name="simple-elements" select="' iati-identifier title '" />
		<add>
			<xsl:for-each select="iati-activities/iati-activity">
				<doc>
				<xsl:for-each select="./*">
					<xsl:if test="contains($simple-elements,name())">
						<field>
							<xsl:attribute name="name">
								<xsl:value-of select="name()" />
							</xsl:attribute>
							<xsl:value-of select="." />
						</field>
					</xsl:if>
				</xsl:for-each>
				</doc>
			</xsl:for-each>
		</add>	
	</xsl:template>
</xsl:stylesheet>
