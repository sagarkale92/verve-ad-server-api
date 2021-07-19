# vgi-video-coding-tests

## Instructions

In this exercise, you will be creating a basic ad server. The ad server
will have an API to return ads, and several APIs to configure ads that
can be returned from the ad API. 

In particular, you will create:

- an API that will take in and retrieve singular **Supply** as JSON (see below)
- an API that will take in and retrieve singular **Deals** as JSON (see below)
- an API that will take in and retrieve singular **Tags** as JSON (see below)
- an ad API that will return an ad in the specified format below. Ads are
  requested using a supply ID, and each response should contain a single ad 
  (distinguishable by the Ad id in the response). Any ad that is applied to
  that Supply can be returned, but if the same supply ID is provided on
  repeated requests, all eligible Tags should eventually be returned.

The relationships between the configuration objects are as follows:
- Supply MAY have associated Tags
- Deals MAY have associated Tags
- Tags MUST have a Deal
- Tags MAY have associated Supply
- Tag and Supply associations MUST be symmetrical (i.e. if a Tag is associated
  with a Supply, then that supply MUST be associated with the Tag)

We have provided a basic skeleton of an application using javalin to create
the routes for these APIs. State does not have to be maintained when the
application is restarted.

The uri for ad requests will look like:
http://localhost:9000/0/ad?supplyId=123

To start the application:
mvn package
java -jar target/tracker-log-service-0.1-SNAPSHOT-jar-with-dependencies.jar

The intent is for this exercise to take you in the ballpark of 2-3 hours. 
Feel free to bring in additional dependencies as relevant.

## References

- [javalin](https://javalin.io/documentation)

## Data Objects

### Supply
```json
{
  "id": 123,
  "name": "Supply",
  "tags": [789, 123]
}
```

### Deal
```json
{
  "id": 456,
  "name": "Deal"
}
```

### Tags
```json
{
    "id": 789,
    "name": "Tag",
    "tagUrl": "https://example.com/my-video.mp4",
    "dealId": 456,
    "supplySources": [123, 456]
}
```

### Ad Response
```xml
<?xml version="1.0" encoding="utf-8"?>
<VAST version="2.0">
	<Ad id="789">
		<InLine>
			<AdTitle>Tag</AdTitle>
			<Creatives>
				<Creative sequence="1">
					<Linear>
						<MediaFiles>
							<MediaFile delivery="progressive" bitrate="256" width="480" height="352" type="video/mp4">https://example.com/my-video.mp4</MediaFile>
						</MediaFiles>
					</Linear>
				</Creative>
			</Creatives>
		</InLine>
	</Ad>
</VAST>
```
