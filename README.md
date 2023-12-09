# YummyTummy

YummyTummy is a delightful Android app that simplifies the process of making delicious meals by providing comprehensive information and step-by-step instructions. The app is developed using Android Studio and the Kotlin programming language, leveraging various libraries and technologies to enhance the user experience.

## Features

- **Meal Information**: Get detailed information about your selected meal, including ingredients, nutritional facts, and more.

- **Video Instructions**: Enjoy a seamless cooking experience with video instructions provided within the app.

- **RecyclerView with DiffUtil**: Utilizing RecyclerView along with DiffUtil for improved performance in displaying and handling lists of meals.

- **Bottom Navigation**: Easy navigation between different sections of the app for a user-friendly experience.

- **Collapsible Toolbar**: A collapsible toolbar adds a touch of elegance to the app's user interface, providing a visually appealing experience.

- **Search Functionality**: Effortlessly find your favorite meals with the implemented search functionality.

## Technologies Used

### Android Studio and Kotlin

The app is built using Android Studio, the official IDE for Android development, and the Kotlin programming language.

### Navigation Component

The Navigation Component is employed to implement a robust navigation system within the app, ensuring a smooth and intuitive user experience.

### Retrofit

Retrofit is used to establish HTTP connections with REST APIs, facilitating the retrieval of meal information. It also converts meal JSON files into Kotlin/Java objects for easy integration.

### Room

The Room library is utilized to store meal data locally in a database. This ensures that users can access their favorite meals even without an internet connection.

### MVVM & LiveData

The MVVM (Model-View-ViewModel) architecture, combined with LiveData, separates logic code from views. This not only enhances code maintainability but also ensures that the app's state is preserved during screen configuration changes.

### Coroutines

Coroutines are employed to perform background tasks efficiently, enhancing the app's responsiveness and user experience.

### View Binding

View Binding is utilized to automate the process of inflating views, streamlining the development workflow and reducing the likelihood of errors.

### Glide

Glide is employed to cache and load images seamlessly into ImageViews, providing a smooth and visually appealing user interface.

### Material Components

Material Components are used to create a consistent and modern design across different Android devices.

## How to Build and Run the App

1. Clone the repository to your local machine.
   ```bash
   git clone https://github.com/your-username/YummyTummy.git
