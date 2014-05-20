<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
   <FlightFinder_FlightsDetailsInfo>
          <xsl:for-each select="list/map/entry/at.tuwien.flightfinder.beans.FlightsDTO">
              <Flight>
                <FlightNumber><xsl:value-of select="FlightNumber"/></FlightNumber>
                <AirCompany><xsl:value-of select="AirCompany"/></AirCompany>
				<NameOrigin><xsl:value-of select="NameOrigin"/></NameOrigin>
				<Destination><xsl:value-of select="Destination"/></Destination>
				<FlightDate><xsl:value-of select="FlightDate"/></FlightDate>
				<Class><xsl:value-of select="Class"/></Class>
				<TicketID><xsl:value-of select="TicketID"/></TicketID>
				<Price><xsl:value-of select="Price"/></Price>
              </Flight>
          </xsl:for-each>
      </FlightFinder_FlightsDetailsInfo>
 </xsl:template>

</xsl:stylesheet>