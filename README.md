# SMP Plugin
Survival Multiplayer Utils for Minecraft using PaperMC API

# Project Rundown
This has been an iterative project I've worked on over several months that provides several miscellaneous utilities for running a survival multiplayer server. It's highly organized, separating its functionality into different modules, hence making it extremely easy to implement features due to a plug-and-play architecture. For instance, to add a new feature, I just create a new class in the 'Functions' package (or other relevant package), write the logic for that feature there, and then initialize it in the 'PluginUtil/Register' class. Disabling a feature is even simpler - just remove/comment out the one initialization line from the Register.

# To-do
I still want to give a rundown on the different modules and what they do, but I have no time to write that right now. For now, just explore the code* (did I mention it's organized?), and check back later for a more exhaustive feature list.

*(head to src/main/java/... for most of the implementation)
