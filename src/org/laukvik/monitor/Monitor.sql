
CREATE TABLE SensorGroup(
	sensorGroupID SERIAL,
	title TEXT CHECK (title <> ''),
	description TEXT,
	PRIMARY KEY(sensorGroupID)
);

CREATE TABLE Sensor (
	sensorID SERIAL NOT NULL,
	sensorGroupID INT REFERENCES SensorGroup(sensorGroupID) NOT NULL,
	title TEXT CHECK (title <> ''),
	description TEXT,
	className TEXT NOT NULL CHECK(className<> ''), 
        settings TEXT,
        numberValue INT8,
	PRIMARY KEY(sensorID)
);

CREATE TABLE History (
	historyID SERIAL NOT NULL,
        sensorID INT REFERENCES Sensor(sensorID) NOT NULL,
        created TIMESTAMP DEFAULT now(),
        numberValue INT8,
	PRIMARY KEY(historyID)
);
