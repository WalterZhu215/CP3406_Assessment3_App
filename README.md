# Nature Explorer

Nature Explorer is an educational Android application designed to help students learn about nature, ecology, biodiversity, and responsible outdoor behaviour through interactive exploration and quizzes.

The application combines educational content, trail exploration, live environmental information, quizzes, persistent learning statistics, personalised settings, and responsible data practices.

The project was developed using Kotlin, Jetpack Compose, Material Design 3, Room Database, Retrofit, ViewModels, Repository Pattern, and Android Navigation.

---

## Target Users

Nature Explorer is designed mainly for secondary school students who are interested in environmental learning and outdoor exploration.

The application aims to support:

- Environmental awareness
- Ecological knowledge
- Memory retention
- Problem-solving
- Responsible outdoor behaviour
- Independent learning

---

## Core Features

### 1. Landing Page

The Home screen acts as the main entry point of the application.

It provides:

- A welcome section
- A featured learning location
- Live environmental information
- Educational environmental notes
- Community contributions
- Navigation to other parts of the application

The Home screen uses Jetpack Compose and Material Design components to provide a clean and responsive interface.

---

### 2. Nature Exploration

The Explore screen allows users to browse different nature trails and learning locations.

Users can:

- Browse available trails
- View trail information
- Open trail details
- Save trails to their collection
- Start a learning quiz related to a trail

The Detail screen connects trail exploration with educational activities.

---

### 3. Interactive Learning Quiz

Nature Explorer includes an interactive ecology quiz as its main learning activity.

The quiz covers topics such as:

- Biodiversity
- Wildlife protection
- Leave No Trace principles
- Invasive species
- Ecosystems
- Carrying capacity
- Pollinators
- Responsible wildlife observation

Three difficulty levels are available:

- Easy – 3 questions
- Medium – 5 questions
- Hard – 7 questions

Quiz logic is separated from the user interface so that it can be tested independently.

After completing a quiz, the score is automatically stored in the local Room database.

---

## Learning Settings

The Settings screen allows students to personalise their learning experience.

Users can change:

- Quiz difficulty
- Application language

Available quiz difficulty levels are:

- Easy
- Medium
- Hard

The selected difficulty directly affects the number and complexity of quiz questions.

The application also supports both English and Chinese interface content.

---

## Learning Statistics

The Statistics screen displays real learning progress based on completed quizzes.

The application calculates and displays:

- Number of quizzes completed
- Average quiz score
- Best quiz score
- Total number of correct answers
- Number of saved learning trails
- Recent quiz results

Quiz statistics are not hard-coded.

They are calculated from quiz results stored persistently in the Room database.

This means the user's learning history remains available after the application is closed and reopened.

---

## Room Database

Nature Explorer uses Room Database for persistent local data storage.

The database currently stores two main types of information:

### Saved Trails

The `saved_trails` table stores trails selected by the user.

Stored information includes:

- Trail name
- Image URL
- Date added

### Quiz Results

The `quiz_results` table stores completed quiz results.

Stored information includes:

- Trail name
- Quiz score
- Total number of questions
- Completion time

Room allows the application to maintain learning progress and saved content between application sessions.

---

## Live Environmental Data

Nature Explorer integrates an external internet API using Retrofit.

The Home screen retrieves current environmental information for the Redwood National Park region.

Displayed information includes:

- Temperature
- Relative humidity
- Wind speed
- Current weather condition
- Observation time

The application uses the Open-Meteo API as the environmental data source.

Users can refresh the information using the refresh button.

If the internet connection or API request fails, the application displays an error message and allows the user to retry instead of crashing.

---

## Architecture

The project follows a layered Android architecture.

The general structure is:

```text
Jetpack Compose UI
        ↓
ViewModel
        ↓
Repository
        ↓
Room Database / External API