
CREATE TABLE SensorGroup(
	sensorGroupID SERIAL,
	title text CHECK (title <> ''),
	description text,
	PRIMARY KEY(sensorGroupID), UNIQUE(sensorGroupID,title)

);

CREATE TABLE Sensor (
	sensorID SERIAL NOT NULL,
	sensorGroupID INT REFERENCES SensorGroup(sensorGroupID) NOT NULL,
	title text CHECK (title <> ''),
	description text,
	className text NOT NULL CHECK(className<> ''), 
	PRIMARY KEY(sensorID), UNIQUE(sensorID,title)
);

