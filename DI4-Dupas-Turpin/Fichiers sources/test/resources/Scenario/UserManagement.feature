Feature: users management
  adding user
  modifying user
  deleting user

  Scenario Outline: adding user
    Given user navigates to user creation module
    When user fills the fields("<name>","<surname>",<age>,"<role>","<mail>","<password>") and add the new user
    Then verify that the user can be find in the database by his email : "<mail>"
    Examples:
      |name     |surname  |age |role    |mail                     |password     |
      |tem      |bob      |18  |MANAGER |bob@outlook.fr           |password     |
      |pivot    |bernard  |54  |VENDEUR |bernard.pivot@outlook.fr |password     |
      |sinclard |paul     |68  |VENDEUR |paul.sinclard@outlook.fr |password     |

  Scenario Outline: modifying user
  Given user selected a user (id:<id>) and click on modifying user
  When user change the fill name by "<name>" and enters
  Then verify that the user has his new "<name>"
  Examples:
   |id  | name |
   |1   | paul |
   |2   |janine|

  Scenario Outline: deleting user
  Given user selected a user (id:<id>) and click on deleting
  When user confirms
  Then verify that the user is not in the database
  Examples:
    |id  |
    |1   |
    |2   |
