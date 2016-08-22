<?xml version ="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"/>

<xsl:template match="/">

  <xsl:for-each select="notes/note">

	To: <xsl:value-of select="to"/>
	<br/>
	From: <xsl:value-of select="from"/>
	<br/>
	Heading: <xsl:value-of select="heading"/>
	<br/>
	Body: <xsl:value-of select="body"/>
	<br/>
	<br/>
	
  </xsl:for-each>
	
</xsl:template>
 
</xsl:stylesheet>