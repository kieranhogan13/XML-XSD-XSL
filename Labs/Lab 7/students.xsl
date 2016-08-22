<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- your code goes here -->
	<xsl:template match="/">
	<html><head><title>Grade Report</title></head><body>
	<xsl:call-template name="print_grade">
	<xsl:with-param name="target_grade" select="'A'"/>
	<xsl:with-param name="list" select="class"/>
	</xsl:call-template>
	<p/>
	<xsl:call-template name="print_grade">
	<xsl:with-param name="target_grade" select="'B'"/>
	<xsl:with-param name="list" select="class"/>
	</xsl:call-template>
	<p />
	<xsl:call-template name="print_grade">
	<xsl:with-param name="target_grade" select="'C'"/>
	<xsl:with-param name="list" select="class"/>
	</xsl:call-template>
	<p />
	</body></html>
	</xsl:template>
</xsl:stylesheet>
