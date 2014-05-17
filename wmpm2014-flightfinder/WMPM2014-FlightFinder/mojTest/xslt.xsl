<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

		<xsl:template match="/">
			<FlightFinder_FlightsDetailsInfo>
       				  <xsl:apply-templates/>
       		</FlightFinder_FlightsDetailsInfo>
	</xsl:template>

	 <xsl:template match="at.tuwien.flightfinder.beans.FlightsDTO">
    	<xsl:copy-of select="."/>
  	</xsl:template>
</xsl:stylesheet>