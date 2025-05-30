# HackVortex 2025
# Domain: AI/ML
# Problem Statement: Revolutionizing Road Safety and Maintenance: An AI-Powered Solution for Predicting Tyre Wear and Recommending Optimal Replacements
# Project : TyreLife AI: Enhancing Road Safety through Predictive Maintenance
# Approach & Solution : 
🔍 Approach:
Our approach is to bridge the gap between traditional tyre inspection methods and modern, accessible AI-driven solutions. We aim to eliminate the need for costly sensors and provide a data-driven, user-centric experience by leveraging image processing and machine learning.

We divided the system into two core modules:

1. Tyre Analyzer (Image-Based Wear Detection)

2. Tyre Predictor (Personalized Tyre Recommendation)

💡 Solution Design:

1️⃣ Tyre Analyzer – Image-Based Tyre Wear Detection
* Input:

1–8 images of tyres captured using a smartphone camera.

Location (city) of the vehicle.

* Process:

Images are passed through a deep learning-based computer vision model trained on tyre wear patterns.

* Key parameters extracted include:

Tread depth pattern

Presence of cracks

Surface degradation

* AI predicts:

Tyre health status: Good / Average / Poor

Estimated remaining life (in days)

Maintenance urgency

* Output:

Clear visual feedback with predictions.

Maintenance alerts based on severity.

2️⃣ Tyre Predictor – Personalized Tyre Recommendation Engine

* Input:

Vehicle Type (e.g., Car, Truck, Bike)

Axle Configuration (Front/Back/All)

City/Region (for climate and road profile)

Vehicle Usage (Daily/Commercial/Travel)

* Process:

Uses predefined ML-based logic or rules to match inputs with tyre performance data and environmental factors.

* Filters tyres best suited for:

Road conditions

Climate (wet/dry/hot terrains)

Vehicle load and usage pattern

* Output:

Top 3 tyre recommendations with:

Brand name

Model/version

Suitability rating

# Features:


📸 1. Image-Based Tyre Wear Detection

Upload 1 to 8 photos of your vehicle’s tyres using a mobile camera.

AI detects:

Tread depth and wear patterns

Surface cracks and abnormalities

Signs of under/over-inflation or ageing

🧠 2. AI-Powered Tyre Health Prediction

Predicts tyre condition: Good / Average / Poor

Estimates:

Safe remaining days of use

Urgency for maintenance or replacement

🎯 3. Personalized Tyre Recommendations

Input key details:

Vehicle type (Car/Bike/Truck)

City/region

Axle configuration

Purpose (Daily/Commercial/Travel)

Provides top 3 best-matching tyre options:

Brand & model

Compatibility

Performance rating based on input conditions

💸 4. Hardware-Free, Cost-Effective Solution

No need for external sensors or devices.

Fully software-based using mobile camera and AI models.

Accessible to individual drivers and fleet managers alike.

📲 5. User-Friendly Mobile Interface

Simple, clean Android app interface.

Easy navigation:

Tyre Analyzer for health check

Tyre Predictor for product suggestions

Quick results within seconds of image upload.

🔄 6. Real-Time & Offline Capabilities

Works in real-time (optional backend or on-device inference).

Can be designed to work partially offline (edge device friendly).

🛣️ 7. Adapted for Indian Road and Weather Conditions

Takes into account city-specific infrastructure and climate for better accuracy.

Helps users from all regions make informed tyre decisions.

# Tech Stack :

🔹 Frontend (Mobile App)

Language: Java

Platform: Android Studio

UI Components: XML Layouts

Device Support: Virtual and physical Android devices

🔹 Backend / Logic Layer

Image Analysis & ML Logic: Gemini API

#  Screenshots :

1. Home Screen :

![image](https://github.com/user-attachments/assets/a46b302e-913b-4e6a-b030-3ba2679f0007)

2. Tyre Analyzer : 

* Before :

![image](https://github.com/user-attachments/assets/28a70a90-ca47-4968-b342-68a21ac7f5c7)

* After : 

![image](https://github.com/user-attachments/assets/83ed16b7-a024-41fb-8949-b97ba40c2edb)

2. Tyre Predictor :

*Before :

![image](https://github.com/user-attachments/assets/94bbbf9d-44a6-4f98-a9a5-6144d77baed1)


*After:

![image](https://github.com/user-attachments/assets/958efec1-1450-4c6f-a1b6-52f0051f8156)

3. Demo Video : 

https://drive.google.com/file/d/1DERk1uklzRzEwksEA1nAarscrVFxkxYF/view?usp=drivesdk

# Run Instructions :

✅ Option 1: Install APK Directly

Download the latest APK from the link below:

👉 Download APK : https://www.mediafire.com/file/3c81clpddppx9pj/app-debug.apk/file

Transfer the APK file to your Android device.

Open the file and install it (you may need to enable “Install from unknown sources” in your device settings).

Launch the app and start analyzing or predicting tyre health!

🧑‍💻 Option 2: Build from Source (Using Android Studio)

Clone the repository

Open the project in Android Studio.

Inside the project gradle, create a new file named:

secrets.properties

Add your Gemini API key inside the file like this:

apikey = "your_gemini_api_key_here"

Sync the Gradle files (Android Studio will prompt this automatically).

Connect an Android device or start an emulator.

Click Run ▶️ to launch the app!

# Team Name : Spartan Techies
# Teammates : Priyanka Chaudhari, Hemal Parikh, Utkarsha Sonawane
