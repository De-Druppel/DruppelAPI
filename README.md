# DruppelAPI

API documentation for Druppel API.

## Allowed requests


|               |                                             |
| ------------- | ------------------------------------------- |
Description     | Get a measurement or list of measurements   |
Endpoint        | `/druppel-api/past-readings/`               |
HTTP Method     | **GET**                                     |




#### Parameters:
| Parameter           | Description                       |
| :------------------ | :-------------------------------- |
| api-key             | API authentication key            |
| days                | Amount of days to request         |
| esp-id              | ID of requested esp device        |
| type `(optional)`   | Type of measurement               |

#### Payload example
``` json
{
    "code": 200,
    "message": "ok",
    "data": {
        "2020-10-26T23:00:00.000+00:00": [
            {
                "value": 8.12,
                "type": "humidity"
            },
            {
                "value": 12.32,
                "type": "temprature"
            }
        ],
        "2020-10-27T23:00:00.000+00:00": [
            {
                "value": 92.13,
                "type": "moisture"
            }
        ]
    }
}
```

## Description of server responses

| Code                | Description                                                  |
| :------------------ | :----------------------------------------------------------- |
| 200 `OK`            | Request was successful                                       |
| 400 `Bad Request`   | Request could not be understood or was missing parameter(s)  |
| 401 `Unauthorized`  | Authentication failed                                        |
| 404 `Not found`     | Request url not found                                        |

## Requirements
- Maven 3.6.3
- JDK 13

## Credits
- Tygo van Ommen
- Nick Kersten
- Iness
