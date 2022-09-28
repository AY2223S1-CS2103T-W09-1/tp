---
layout: page
title: User Guide
---

bobaBot is a **desktop application** for managing customers’ membership details. It is **optimized for Command Line Interface (CLI) while retaining some benefits of the Graphical User Interface (GUI)**. If you are a cashier working at a bubble tea shop and can type fast, bobaBot can help you easily find and manage your customers’ membership information as compared to other GUI applications.
* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `bobaBot.jar` from [here](https://github.com/AY2223S1-CS2103T-W09-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your bobaBot.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * **`list`** : Lists all customers.

    * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a customer named `John Doe` to bobaBot.

    * **`delete`**`3` : Deletes the 3rd customer shown in the current list.

    * **`clear`** : Deletes all customers.

    * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a Customer: `add`

Adds a Customer to bobaBot.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [r/REWARD] [t/TAG]…`



<div markdown="span" class="alert alert-primary">:bulb: Tip:
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com r/0 t/new `
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com p/1234567 r/5000 t/gold`

### Updating a Customer’s details: `update`

Updates an existing Customer in bobaBot.

Format: `update n/NAME OR update p/PHONE_NUMBER OR update e/EMAIL
[n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [r/REWARD] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: Tip:
At least one of the optional fields must be provided
</div>

Examples:
* `update n/John Doe p/91234567 e/johndoe@example.com r/1000`
* `update n/Peter Parker r/420`

### Listing all customers : `list`

Shows a list of all customers in the address book.

Format: `list`

### Editing a customer : `edit`

Edits an existing customer in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the customer at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the customer’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st customer to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd customer to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds customers whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Customers matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a Customer : `delete`

Removes a Customer from bobaBot.

Format:

`delete n/NAME` or

`delete p/PHONE_NUMBER` or

`delete e/EMAIL`

* Deletes the Customer with the following `NAME` when `n/` specified.
* Deletes the Customer with the following `PHONE_NUMBER` when `p/` specified.
* Deletes the Customer with the following `EMAIL` when `e/` specified.

Examples:
* `delete n/Alex Yeoh` removes the Customer with the name `Alex Yeoh`.
* `delete p/87438807` removes the Customer with the phone number `87438807`.
* `delete e/alexyeoh@example.com` removes the Customer with the email `alexyeoh@example.com`.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                                                                                                                             |
|------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL [r/REWARD] [t/TAG]…` <br> e.g., ` add n/Betsy Crowe t/friend e/betsycrowe@example.com p/1234567 r/5000 t/gold`                                            |
| **Update** | `update n/NAME` or `update p/PHONE_NUMBER` or `update e/EMAIL [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [r/REWARD] [t/TAG]…​`<br> e.g.,`update n/John Doe p/91234567 e/johndoe@example.com r/1000` |
| **Delete** | `delete n/NAME` or `delete p/PHONE_NUMBER` or `delete e/EMAIL` <br> e.g., `delete n/Alex Yeoh`, `delete p/87438807`, `delete e/alexyeoh@example.com`                                         |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find alex david`                                                                                                                                   |
| **Clear**  | `clear`                                                                                                                                                                                      |
| **List**   | `list`                                                                                                                                                                                       |
| **Help**   | `help`                                                                                                                                                                                       |
| **Exit**   | `exit`                                                                                                                                                                                       |

