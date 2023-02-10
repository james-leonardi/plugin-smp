package me.pao.smp.Functions;

import me.pao.smp.C;
import me.pao.smp.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MiscCommands implements CommandExecutor {

    private final Main instance;
    public MiscCommands(){
        instance = Main.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (l.equalsIgnoreCase("smprl")){
            instance.reloadConfig();
            s.sendMessage("SMP config reloaded");
        }
        if (l.equalsIgnoreCase("ib")){
            s.sendMessage("IB: The International Baccalaureate Program is an organization dedicated to the predation of young minds through manipulation and psychological brainwashing. It corrupts students into believing that they are part of a privileged and elite society of more intelligent beings and raises its own prestige by forcing students to commit to nineteen hour work days, thereby causing parents to believe that the massive and unreasonable amount of travail must equate with a higher education. This in turn deters the ordinarily rational parents from protesting when asked to pay absurd amounts of money for exams that their children grow to despise. It is not until their fourth year in this fraudulent program that the participants realize that this course has no effect on the colleges to which they intend to apply. By this time, the student has already dedicated thousands of hours to this corporation (doubt not that it is indeed a corporation) and human nature insists that they complete the program. Its long, unpronounceable and un-spellable name only adds to its supposed prominence. In short, this institution is designed to swindle valuable money and time away from its members by exploiting competitive young adults.");
        }
        if (l.equalsIgnoreCase("brew")){
            s.sendMessage("Click the following link to open the brewing guide:");
            s.sendMessage(ChatColor.BLUE + "" + ChatColor.ITALIC + C.brewingGuideLink);
        }
        return true;
    }
}
