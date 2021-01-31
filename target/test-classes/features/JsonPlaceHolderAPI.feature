Feature: Verify whether fetch movies API 

Scenario: Verify Creation Of Resource
Given Upcoming movies api
When User calls "Addjsonplacehoder" API with "POST" HTTP Request
Then User should display status code as "201"
