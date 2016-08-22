<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
<xsl:template match="/">
<html><head><title>Display Shipment</title></head><body>

	your code here...
	
</body></html>

</xsl:template>

<xsl:template match="/shipment/item">

	your code here...

</xsl:template>	

<xsl:template name="write_total_weight">
	<xsl:param name="total_weight" />
	<xsl:param name="total_weight_uom" />
	
	your code here...	
	
</xsl:template>
	
</xsl:stylesheet>	
