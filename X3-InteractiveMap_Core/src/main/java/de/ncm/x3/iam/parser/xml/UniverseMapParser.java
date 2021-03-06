
package de.ncm.x3.iam.parser.xml;


import java.io.File;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ncm.jgoodies.model.HashMapModel;
import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Race;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.SpaceStation;
import de.ncm.x3.iam.data.universe.UniverseMap;
import de.ncm.x3.iam.data.universe.WarpGate;
import de.ncm.x3.iam.data.universe.WarpGateConstants;

public class UniverseMapParser extends XMLParser<UniverseMap> {
	
	private static Logger logger = Logger.getLogger(UniverseMapParser.class);
	private Node mapNode;
	private Node racesNode;
	
	public UniverseMapParser(File logFile) {
		super(logFile);
		logger.info("LogFile: " + logFile);
	}
	
	@Override
	public UniverseMap parseXML(Element rootElement) {
		NodeList children = rootElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Map")) {
				this.mapNode = children.item(i);
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Races")) {
				this.racesNode = children.item(i);
			}
		}
		
		HashMapModel<Integer, Race> raceMap = parseRaces(racesNode);
		return parseMap(raceMap, mapNode);
	}
	
	private HashMapModel<Integer, Race> parseRaces(Node item) {
		
		HashMapModel<Integer, Race> raceMap = new HashMapModel<Integer, Race>();
		NodeList children = item.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Race")) {
				parseRace(raceMap, children.item(i));
			}
		}
		return raceMap;
	}
	
	private void parseRace(HashMapModel<Integer, Race> raceMap, Node mapNode) {
		NodeList children = mapNode.getChildNodes();
		
		Integer id = 0;
		String name = "";
		
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Name")) {
				name = children.item(i).getFirstChild().getNodeValue();
			} else if (children.item(i).getNodeName().equalsIgnoreCase("ID")) {
				id = Race.getIDFromRawString(children.item(i).getFirstChild().getNodeValue());
			}
		}
		raceMap.put(id, new Race(id, name));
	}
	
	private UniverseMap parseMap(HashMapModel<Integer, Race> raceMap, Node item) {
		NodeList children = item.getChildNodes();
		UniverseMap map = new UniverseMap();
		map.setRaces(raceMap);
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Sector")) {
				parseSector(raceMap, map, children.item(i));
			}
		}
		return map;
	}
	
	private void parseSector(HashMapModel<Integer, Race> raceMap, UniverseMap map, Node item) {
		
		NodeList children = item.getChildNodes();
		
		Sector sector = new Sector();
		GridPos gc = new GridPos();
		
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Name")) {
				sector.setName(getStringValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("X")) {
				gc.setGridX(getIntValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Y")) {
				gc.setGridY(getIntValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("RaceID")) {
				Integer raceID = Race.getIDFromRawString(getStringValueOf(children.item(i)));
				sector.setRace(raceMap.get(raceID));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("GateConnections")) {
				parseGateConnections(sector, children.item(i));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Stations")) {
				parseStations(sector, children.item(i));
			}
		}
		map.putSector(gc, sector);
		
	}
	
	private void parseStations(Sector sector, Node item) {
		NodeList children = item.getChildNodes();
		
		for (int i = 0; i < children.getLength(); i++) {
			SpaceStation station = new SpaceStation();
			Node child = children.item(i);
			parseStation(station, child);
			sector.addSpaceStation(station);
		}
	}
	
	private void parseStation(SpaceStation station, Node item) {
		NodeList children = item.getChildNodes();
		String clazz = "";
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeName().equalsIgnoreCase("Name")) {
				station.setName(getStringValueOf(child));
			} else if (child.getNodeName().equalsIgnoreCase("X")) {
				station.setPosX(getIntValueOf(child));
			} else if (child.getNodeName().equalsIgnoreCase("Y")) {
				station.setPosY(getIntValueOf(child));
			} else if (child.getNodeName().equalsIgnoreCase("Z")) {
				station.setPosZ(getIntValueOf(child));
			} else if (child.getNodeName().equalsIgnoreCase("Type")) {
				station.setType(getStringValueOf(child));
			} else if (child.getNodeName().equalsIgnoreCase("StationClass")) {
				clazz = getStringValueOf(child);
			}
		}
		if (!station.getName().trim().equals("")) {
			System.out.println(station.getName() + "\t: " + clazz);
		}
	}
	
	private void parseGateConnections(Sector sector, Node item) {
		
		NodeList children = item.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Sector")) {
				if (children.item(i).getNodeName().equalsIgnoreCase("Sector")) {
					String name = "";
					GridPos gridPos = new GridPos(-1, -1);
					
					NodeList sectorNode = children.item(i).getChildNodes();
					for (int j = 0; j < sectorNode.getLength(); j++) {
						if (sectorNode.item(j).getNodeName().equalsIgnoreCase("Name")) {
							name = sectorNode.item(j).getFirstChild().getNodeValue();
						} else if (sectorNode.item(j).getNodeName().equalsIgnoreCase("X")) {
							gridPos.setGridX(Integer.parseInt(sectorNode.item(j).getFirstChild().getNodeValue()));
						} else if (sectorNode.item(j).getNodeName().equalsIgnoreCase("Y")) {
							gridPos.setGridY(Integer.parseInt(sectorNode.item(j).getFirstChild().getNodeValue()));
						}
					}
					WarpGate wg = null;
					if (!gridPos.equals(new GridPos())) {
						wg = new WarpGate(gridPos);
					}
					
					if (name.equalsIgnoreCase("NorthGate")) {
						sector.setWarpGate(WarpGateConstants.WARPGATE_NORTH, wg);
					} else if (name.equalsIgnoreCase("SouthGate")) {
						sector.setWarpGate(WarpGateConstants.WARPGATE_SOUTH, wg);
					} else if (name.equalsIgnoreCase("EastGate")) {
						sector.setWarpGate(WarpGateConstants.WARPGATE_EAST, wg);
					} else if (name.equalsIgnoreCase("WestGate")) {
						sector.setWarpGate(WarpGateConstants.WARPGATE_WEST, wg);
					}
					
				}
			}
		}
	}
	
}
