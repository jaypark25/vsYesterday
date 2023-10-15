# vsYesterday



## CPSC210 Assignment Phase 0 ##

***What will the application do?***

vsYesterday is a habit tracker app that challenges you to take one step forward from your yesterday self. 
This app lets you input any *activities / challenges* that you'd like to build
into your habit and how they will be tracked. 
For example: 
- **Folding Laundry** - Time Challenge (Trying to complete task in the shortest time)
- **Squats** - Maximizing Reps (Attempting as many reps as possible)
- **Studying** - Self-Time (Trying to spend as much time to do x activity for improvement.)

Every day, the user will be able to record and enter their progress for their activity.
If the activity involves tracking time, there will be a ***built-in stopwatch feature*** to log the time spent.
When users log their progress each day, an analytical screen will show up, and show their recent progress in the
past week and month, and display words of encouragement. 

***Who will use it?***

vsYesterday will be mostly used by teens / young adults that want to introduce positive habits into their lifestyle.
More than ever, *productivity and efficiency* has become a macro trend for this audience.
By making each habit compared to the user's tracking the day before, vsYesterday creates a gamification twist
on the common habit trackers in the market.

***Why is the project of Interest to you?***

I've become motivated to make vsYesterday, because as someone who wanted to develop themselves over time, 
it was difficult to track progress on my various types of challenges.
vsYesterday is an app that I would have loved in my self-development journey; I had the motivation to incorporate good 
habits, but struggled to keep a  consistent schedule and tracking. 


**USER STORIES**
- As a user, I want to be able to create a new challenge / activity to my currently-tracked tasks.
- As a user, I want to record the progress for each challenge every day.
- As a user, I want to be able to see an analytical screen after my day's challenges have been recorded. 
- As a user, I want to be able to delete a challenge from tracked tasks.
- As a user, I want to be able to see a progress graph for each task in a given time period. 
- As a user, I want to be able to save my habit list, if wanted.
- As a user, I want to be able to reload my habit list from my saved files
- As a user, I want to be able to add multiple Habits to a HabitList.
- 
# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by going to Edit, Create a new Habit, enter a 
- name of habit. Then, back in the main menu, you can click "Record Day", where you can check off each habit.
- You can also add multiple Habits, by going back to the create Habit menu.
- This allows you to add history to each habit.

- You can generate the second required action related to adding Xs to a Y by going to Edit, then remove a habit.
- You must enter the exact name of the habit to remvoe it.
- You can locate my visual component by looking at the Main Screen. BLACKPINK <3
- You can save the state of my application by clicking the save button.
- You can reload the state of my application by clicking the load button.
- 
- 
- Phase 4: Task 2
  - Sun Apr 09 16:20:34 PDT 2023
  - Drink Water Habit Added
  - Sun Apr 09 16:20:43 PDT 2023
  - Do workout Habit Added
  - Sun Apr 09 16:20:45 PDT 2023
  - Drink WaterDay Recorded
  - Sun Apr 09 16:20:48 PDT 2023
  - Do workoutDay Recorded
  - Sun Apr 09 16:20:58 PDT 2023
  - Drink Water Habit Removed.
  - Sun Apr 09 16:21:00 PDT 2023
  - File Saved.


Looking back at the UML class diagram presented for the HabitTrackerApp, one possible refactoring that could 
support the program is having the GUI in a different class. Currently, the existing "HabitTrackerApp" has extreme 
coupling errors.

Another thing that could help support the system is by having my Abstract habit class more proper. At the beginning of 
the term, I couldnt find a way to properly support this, so I used a loop-around, which made the code duplication.