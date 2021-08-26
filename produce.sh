#! /bin/bash
while true; do
  curl --location --request PUT 'http://localhost:8080/kafka/create' --header 'Content-Type: application/json' --data-raw '{ "assetId": 77, "tenantId": 8778, "scanCode": 77, "name": "Hammer Hilti 1888999" }'
  curl --location --request PUT 'http://localhost:8080/kafka/create' --header 'Content-Type: application/json' --data-raw '{ "assetId": 75, "tenantId": 8778, "scanCode": 77, "name": "Hammer Hilti 1888999" }'
  sleep 0.1
done
