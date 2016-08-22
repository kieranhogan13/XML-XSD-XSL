<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:s="http://www.dit.ie">
	
<xsl:template match="/">
<html><head><title>Display Shipment</title></head><body>

	<b>Shipment Number:</b><xsl:text> </xsl:text><xsl:value-of select="s:shipment/@shipmentid"/><br/><br/>
	<table colspan="2">
	<tr><td>
		<table colspan="2">
			<tr><td><b>Consignor:</b></td><td><xsl:value-of select="s:shipment/s:consignor/s:name"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignor/s:address_line_1"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignor/s:address_line_2"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignor/s:address_line_3"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignor/s:address_line_4"/></td></tr>
		</table>
	</td>
	<td valign="top">
		<table colspan="2">
			<tr><td><b>Consignee:</b></td><td><xsl:value-of select="s:shipment/s:consignee/s:name"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignee/s:address_line_1"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignee/s:address_line_2"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignee/s:address_line_3"/></td></tr>
			<tr><td></td><td><xsl:value-of select="s:shipment/s:consignee/s:address_line_4"/></td></tr>
		</table>
	</td></tr>
	</table>
	
	<br/>
	<b>Summary:</b><br/> 
	<xsl:variable name="num-items" select="count(s:shipment/s:item)"/>
	Total number of items: <xsl:value-of select="$num-items"/><br/>
	Total Weight:
		<xsl:call-template name="write_total_weight">
		<xsl:with-param name="total_weight" select="s:shipment/s:details/s:total_weight"/>
		<xsl:with-param name="total_weight_uom" select="s:shipment/s:details/s:total_weight_uom"/>
	</xsl:call-template>
	Average Weight Per Item Line: <xsl:value-of select="s:shipment/s:details/s:total_weight div $num-items"/><br/>
	Total quantity: <xsl:value-of select="sum(s:shipment/s:item/s:quantity)"/><br/>
	Total value: <xsl:value-of select='format-number(sum(s:shipment/s:item/s:price), "#.00")' /><br/><br/>
	
	<table colspan="6">
		<th>Sequence</th><th>ID</th><th>Title</th><th>Note</th><th>Quantity</th><th>Price</th><th>Line Total</th>
	
		<xsl:apply-templates select="s:shipment/s:item"/>
	
	</table>	
	
</body></html>

</xsl:template>

<xsl:template match="/s:shipment/s:item">

	<tr>
		<td><xsl:number value="position()"/></td>
		<td><xsl:value-of select="s:id"/></td>
		<td><xsl:value-of select="s:title"/></td>
		<td><xsl:value-of select="s:note"/></td>
		<td><xsl:value-of select="s:quantity"/></td>
		<td><xsl:value-of select="s:price"/></td>
		<td><xsl:value-of select='format-number(sum(s:price), "#.00")' /></td>
	</tr>	

</xsl:template>	

<xsl:template name="write_total_weight">
	<xsl:param name="total_weight" />
	<xsl:param name="total_weight_uom" />
	
	<xsl:value-of select="$total_weight"/>
	<xsl:text> </xsl:text>
	<xsl:value-of select="$total_weight_uom"/><br/>
	
</xsl:template>
	
</xsl:stylesheet>	
