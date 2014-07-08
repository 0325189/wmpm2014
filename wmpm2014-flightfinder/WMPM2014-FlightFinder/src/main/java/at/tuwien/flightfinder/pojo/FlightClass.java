package at.tuwien.flightfinder.pojo;

import javax.xml.bind.annotation.*;
/**
 * Flight class enum
 * @author Ivan Gusljesevic
 */

@XmlType(name = "FlightClass")
@XmlEnum
public enum FlightClass {
	Business,
	Economy;
}
