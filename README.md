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
