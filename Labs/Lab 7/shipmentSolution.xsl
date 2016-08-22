<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
<xsl:template match="/">
<html><head><title>Display Shipment</title></head><body>

	<b>Shipment Number:</b><xsl:text> </xsl:text><xsl:value-of select="shipment/@shipmentid"/><br/><br/>
	<table colspan="2">
	<tr><td>
		<table colspan="2">
			<tr><td><b>Consignor:</b></td><td><xsl:value-of select="shipment/consignor/name"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignor/address_line_1"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignor/address_line_2"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignor/address_line_3"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignor/address_line_4"/></td></tr>
		</table>
	</td>
	<td valign="top">
		<table colspan="2">
			<tr><td><b>Consignee:</b></td><td><xsl:value-of select="shipment/consignee/name"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignee/address_line_1"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignee/address_line_2"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignee/address_line_3"/></td></tr>
			<tr><td></td><td><xsl:value-of select="shipment/consignee/address_line_4"/></td></tr>
		</table>
	</td></tr>
	</table>
	
	<br/>
	<b>Summary:</b><br/> 
	<xsl:variable name="num-items" select="count(shipment/item)"/>
	Total number of items: <xsl:value-of select="$num-items"/><br/>
	Total Weight:
		<xsl:call-template name="write_total_weight">
		<xsl:with-param name="total_weight" select="shipment/details/total_weight"/>
		<xsl:with-param name="total_weight_uom" select="shipment/details/total_weight_uom"/>
	</xsl:call-template>
	Average Weight Per Item Line: <xsl:value-of select="shipment/details/total_weight div $num-items"/><br/>
	Total Quantity: <xsl:value-of select="sum(shipment/item/quantity)"/><br/>
	<br/>
	
	<table colspan="6">
		<th>Sequence</th><th>ID</th><th>Title</th><th>Note</th><th>Quantity</th><th>Price</th><th>Line Total</th>
	
		<xsl:apply-templates select="shipment/item"/>
	
	</table>	
	
</body></html>

</xsl:template>

<xsl:template match="/shipment/item">

	<xsl:variable name="line-qty" select="quantity"/>
	<xsl:variable name="line-price" select="price"/>
	<xsl:variable name="line-total" select="($line-qty * $line-price)"/>

	<tr>
		<td><xsl:number value="position()"/></td>
		<td><xsl:value-of select="id"/></td>
		<td><xsl:value-of select="title"/></td>
		<td><xsl:value-of select="note"/></td>
		<td><xsl:value-of select="quantity"/></td>
		<td><xsl:value-of select="price"/></td>
		<td><xsl:value-of select='format-number($line-total, "#.00")' /></td>
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
