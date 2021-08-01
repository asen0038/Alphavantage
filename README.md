# SCD2_2021_Exam

## APIs targeted

Input API: Alphavantage

Output API: Twilio SMS output

## Implementation target Level

CREDIT

## TDD commit history

The following list shows the commit history in the following format "RED SEQUENCE COMMIT URL" : "GREEN SEQUENCE COMMIT URL"

1. f3651e1516d0f786ff6818869d2bb9f2d7f2a8ca : fa489696f8ce78ce62683cd7197b56651bd5a3b1

Extension TDD commit list in the format "RED SEQUENCE COMMIT URL" : "GREEN SEQUENCE COMMIT URL"

1. 42395fe4cebc985ac7fe06e609d511705155cde0 : b0cc9bfbecc4b3c8a109fe5f9e68228a2ade1659

## Configuration required

Please treat this configuration as a prerequsite before running the application.
Open the configuration folder, there you will find a json file called
credentials.json. Open the file and enter the details between the quotation marks.

The information to enter in the fields and their respective meanings are as follows :-

1. in_key: Your input api key from alphavantage
2. sid: Your twilio account sid
3. out_key: Your output api key from twilio
4. to: The phone number you  want to send sms 'to'
5. from: The phone number you  want to send sms 'from'

Note: Include the country code along with the phone number. ie: +910000000000. Do not include any spaces.

## How to run the program:

Build the program with 'gradle build'. Then there can be either 4 types of runs depending 
on the user by typing either of the following :-

1. gradle run --args="online online"
2. gradle run --args="offline online"
3. gradle run --args="online offline"
4. gradle run --args="offline offline"

Note: When interacting with the real API, please allow atleast 4 seconds to recieve or send data.
