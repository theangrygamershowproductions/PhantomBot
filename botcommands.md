# Quicklinks

* [PhantomBot README](README.md "General Readme")
* [Admin Commands](admincommands.md "Admin Commands")
* [Bot Commands](botcommands.md "Commands")
* [Currency Commands](currencycommands.md "Currency Help")

# Discord Bot Commands:

## !botcommands:

    Usage: <b>!botcommands</b>

            Commands: !8ball, !account, !addcom, !aliascom, !bitshandler, !botcommands, !channelcom, !clipschannel, !clipsmessage, !clipstoggle, !commands, !coolcom, !cooldown, !delalias, !delcom, !editcom, !followhandler, !gamble, !gambling, !greetingssystem, !hosthandler, !keyword, !kill, !lastclip, !moderation, !module, !permcom, !points, !pricecom, !promote,!promoteadm, !random, !removegame, !rolemanager, !roll, !roulette, !setgame, !setstream, !slot, !streamelementshandler, !streamhandler, !streamlabshandler, !subscribehandler, !tipeeestreamhandler, !topclip, !twitterhandler

                     Prints list of commands available to the Dicord Bot.

## !8ball:

    Usage: <b>!8ball [question]</b>
    
         Ask the magic-8ball a question with <b>!8ball [question]</b> 
         Should look like this <b>!8ball How are you doing today?</b>

## !account:

    Usage: <b>!account [link / remove]</b>Y
    
        Change it using <b>!account link</b> or remove it using <b>!account remove</b>

## !addcom:

    Usage: <b>!addcom [command] [response]</b>

## !aliascom:

    Usage: <b>!aliascom [alias] [command]</b>

## !bitshandler:

    Usage: <b>!bitshandler [toggle / message / channel]</b>

    Usage: <b>!bitshandler toggle</b>  Turns Bit announcements On or Off

    Usage: <b>!bitshandler message [message] - Tags: (name) (amount)</b>

    Usage: <b>!bitshandler channel [channel name]</b> 

                <b>!bitshandler channel channelname</b>

                -output=Bit announcements will now be made in channel #channelname 

## !channelcom:

    Usage: <b>!channelcom [command] [channel / --global / --list]</b>
    
        Separate the channels with commas (no spaces) for multiple.
 
## !clipschannel:

    Usage: <b>!clipschannel (channel).  Currently:</b>
  
## !clipsmessage:

    Usage: <b>!clipsmessage (message) - Tags: (name), (url)</b>

## !clipstoggle:

    Usage: <b>!clipstoggle</b> Toggles Clip Announcements On and Off

## !commands:

    Usage: <b>Currently None</b>

## !coolcom:

    Usage: <b>!coolcom [command] [seconds] [type (global / user)]</b> - Using -1 for the seconds removes the cooldown.

## !cooldown:

    Usage: <b>!cooldown [setdefault]</b> Sets default cooldown time limit (set in minutes)

## !delalias:

    Usage: <b>!delalias [alias]</b> Deletes Alias Name 

## !delcom:

    Usage: <b>!delcom [command] [response]</b>  Deletes custom command

## !editcom:

    Usage: <b>!editcom [command] [response]</b> Edit custom command

## !followhandler:

    Usage: <b>!followhandler [toggle / message / channel]</b>

## !gamble:

    Usage: <b>!gamble [amount]</b> If you do not have enough points this message will appear.  "You don't have that many points."

## !gambling:

    Usage: <b>!gambling [setmax / setmin / setwinningrange / setgainpercent]</b>

## !greetingssystem:

    Usage: <b>!greetingssystem [jointoggle / parttoggle / joinmessage / partmessage / joinrole / channel]</b>

## !hosthandler:

    Usage: <b>!hosthandler [toggle / hostmessage / autohostmessage / channel]</b>

## !keyword:

    Usage: <b>!keyword [add / edit / remove]</b>

## !kill:

    Usage: <b>!kill</b> Player died in a tragic manner.

## !lastclip:

    Usage: <b>!lastclip</b>  If there currently is no saved clips you will get this message/ "Last Clip: No clip found"

## !moderation:

    Usage: <b>!moderation [links / caps / spam / blacklist / whitelist / cleanup / logs / togglecbenni]</b>

## !module:

Usage: <b>!module [enable / disable / list]</b>

    <b>!module list</b> 
    -ouput= ./discord/commands/customCommands.js [enabled]
            ./discord/games/8ball.js [enabled]
            ./discord/games/gambling.js [enabled]
            ./discord/games/kill.js [enabled]
            ./discord/games/random.js [enabled]
            ./discord/games/roll.js [enabled]
            ./discord/games/roulette.js [enabled]
            ./discord/games/slotMachine.js [enabled]
            ./discord/handlers/bitsHandler.js [enabled]
            ./discord/handlers/clipHandler.js [enabled]
            ./discord/handlers/followHandler.js [enabled]
            ./discord/handlers/hostHandler.js [enabled]
            ./discord/handlers/keywordHandler.js [enabled]
            ./discord/handlers/streamElementsHandler.js [enabled]
            ./discord/handlers/streamHandler.js [enabled]
            ./discord/handlers/streamlabsHandler.js [enabled]
            ./discord/handlers/subscribeHandler.js [enabled]
            ./discord/handlers/tipeeeStreamHandler.js [enabled]
            ./discord/handlers/twitterHandler.js [enabled]
            ./discord/systems/greetingsSystem.js [enabled]
            ./discord/systems/pointSystem.js [enabled]
            ./discord/systems/promoteSystem.js [enabled]

## !permcom:

    Usage: <b>Not Used Currently</b>

## !points:

    Usage: <b>!points</b> -ouput= You currently have X points.

## !pricecom:

    Usage: <b>!pricecom [command] [amount]</b

## !promote:

    Usage: <b>!promote add [short bio] | delete</b> - Add or delete yourself from being promoted.

## !promoteadm:

    Usage:  <b>!promoteadm add | delete | so | channel | streamchannel | revoke | allow | toggleselfmanage | togglestats | togglebanner | list | setinterval</b>

## !random:

    Usage: <b>!random</b> - Gives out random messages in chat

## !removegame:

    Usage: <b>!removegame</b> - Removes games

## !rolemanager:

    Usage: <b>!rolemanager [togglesyncpermissions / togglesyncranks / blacklist]</b>

## !roll:

    Usage: <b>!roll</b> - Rolls Dice and displays message

## !roulette:

    Usage: <b>!roulette</b> - Plays Russian Roulette with the computer

## !setgame:

    Usage: <b>!setgame [game name]</b>

## !setstream:

    Usage: <b>!setstream [twitch url] [game name]</b>

## !slot:

    Usage: <b>!slot</b> Plays the slot machine.  Output to console with roll

## !streamelementshandler:

    Usage: <b>!streamelementshandler [toggle / message / channel]</b>

## !streamhandler:

    Usage: <b>!streamhandler [toggleonline / onlinemessage / toggleoffline / offlinemessage / togglegame / gamemessage / togglebotstatus / channel]</b>

## !streamlabshandler:

    Usage: <b>!streamlabshandler [toggle / message / channel]</b>

## !subscribehandler:

    Usage: <b>!subscribehandler [giftsubmessage / submessage / primemessage / resubmessage / giftsubtoggle / subtoggle / primetoggle / resubtoggle / channel]</b>

## !tipeeestreamhandler:

    Usage: <b>!tipeeestreamhandler [toggle / message / channel]</b>

## !topclip:

    Usage: <b>!topclip</b> -ouput=Today's Most Viewed Clip: No clip found

## !twitterhandler:

    Usage: <b>!twitterhandler [toggle / channel]</b>




This document will be updated periodically with new commands that may be added in the future.
