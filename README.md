Program #4

Aisha Balogun

Cosc 4730

Description: Connect your phone and click on play. I tested with a Android emulator (Nexus 5x API 29 x86). On first run, an empty screen (just with the app name) would come up. Click on the add button on the lower right corner and enter the data into the dialog box. If the user does not enter anything for name, category, date or amount, it would not create the expense entry and would display a message asking you to enter in the missing entry value (the first missing entry value). Clicking on cancel on the dialog box would take you back to the main app screen and clicking on save would save the data entered into the database and display it on the main screen. To edit an existing entry, click on the cardview of that entry and the dialog box would come up to change the existing data. Swipe the cardview either left or right to delete it. 

Anything that doesn't work: N/A

Side Note: For some unknown reason, I had to clean my build every time before I ran my program or it would throw some weird java error that had nothing to do with my actual code; java would just fail to compile. 


# Graded: 48/50 #

* The decimal button for Amount field input is not working. **(-2 points)**

The instructions state that Amount is a Real/Float, however, without the ability to enter decimal values, you are only capable of essentially integers (despite the actual data type). Remember that these Amounts would essentailly be dollar transactions and would therefore need to be able to handle 2 decimal places of input (for cents).
