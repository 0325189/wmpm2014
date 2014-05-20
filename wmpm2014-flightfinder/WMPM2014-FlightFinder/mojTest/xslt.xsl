<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:template match="/">
		<FlightFinder_FlightsDetailsInfo>
          	<xsl:for-each select="list/map/entry/at.tuwien.flightfinder.beans.FlightsDTO">
              <Flight>
                <xsl:copy-of select="child::*"/>
              </Flight>
          	</xsl:for-each>
      	</FlightFinder_FlightsDetailsInfo>
	</xsl:template>

</xsl:stylesheet>