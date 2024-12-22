Welcome to my version of Simulation Java project!
First of all, thanks for checking it, if i can get your feedback it would be awesome and also it would help me a lot.

About build. 
Main method is at Starter class. When you run it, there'll be a discription with commands to pause/resume/stop simulation. 
Don't forget them, because simulation iterations switch very quickly, so you may not notice an notification of this commands while stopping or pausing and would be have to scroll up to find them..

At the console field, every creature have same algoritm of representation:
First number (left from creature's sprite) is current creature's health. It can be decreased based on hunger level or in case of predator attack (for herbivores)
Then you can see actual creature's sprite, and the following character is creature's aim: (e) - creature wants to eat, (s) - creature wants to reproduce.
So final representation for creatures will be like (current health/creature's sprite/current aim)

Balance of predators/herbivores can be regulated by predator attack damage, max hunger level to cancel attempts of reproducing, recovering health and som other parameters.
Current balance set to be optimal, but random start positions can cause predators extinction, so i've make adding herbivores to minimum quantity every turn action.
Btw predators still have chance go extinct, because making them too strong causing uncontrolled and unhindered reproduction, so you can just stop it and run new one.

Reproducing method was made to prevent uncontrolled action, so every creature that has wish to reproduce set only same wishers as [possible targets.
More over, when creature reaches another one, it's one more verefication that both of them want to reproduce, and only if it's true, reproduction action may be started.

