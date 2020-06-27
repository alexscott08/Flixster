# Project 2 - *Flixster*

**Flixster** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **22** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings and popularity within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

The following **additional** features are implemented:

* [x] Added a custom ActionBar
* [x] Added a progress bar to show when an image is loading.
* [x] Added a landscape mode for the details activity.
* [x] Added details on the release date of the movie.
* [x] Added a specific button to allow for the user to transition to the details page.
* [x] Added ellipses on the overview of the movie on the main page and a scrollview on the details page.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://imgur.com/L0Xnlls.gif' width='' alt='Video Walkthrough' /> (Portrait)

<img src='https://imgur.com/LB8aVsM.gif' width='' alt='Video Walkthrough' /> (Landscape)


GIF created with [Kap](https://getkap.co/).

## Notes

I had difficulties getting the YouTube Player to function, but this was due to a misunderstanding of asynchronous calls.
I was not able to implement adding a play button to the MainActivity screen as because I did not have time to calculate how exactly 
to calculate the position of the click (two different actions could take place depending on the location of the click). This is 
something I will continue to work on in the future.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2020] [Alex Scott]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
