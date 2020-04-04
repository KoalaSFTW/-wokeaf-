package Responses;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.javacord.api.entity.permission.Role;

public class Responses {

        public static void main(String[] args)
        {
            String token = "Njk0NTk0MzAwODcyMjI4OTA2.XoO8dg.4Hl-vhaCzKEFT4yfg6JwFaoomnE";
            Random random = new Random();

            //ToDo Import files on startup

            List<Player> playerList = new ArrayList<Player>();
            DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
            {
                api.addMessageCreateListener(messageCreateEvent -> {
                    if (messageCreateEvent.getMessageContent().toLowerCase().contains("you're a third rate duelist")) {
                        messageCreateEvent.getChannel().sendMessage("With a fourth rate deck!");
                    }
                });
                api.addMessageCreateListener(messageCreateEvent -> {
                    Long authorid = messageCreateEvent.getMessageAuthor().getId();
                    if (messageCreateEvent.getMessageContent().toLowerCase().contains("needlefiber is a good card")) {
                        messageCreateEvent.getChannel().sendMessage(String.format("Ban <@%s>", authorid));
                    }
                });
                api.addMessageCreateListener(messageCreateEvent -> {
                    Long authorid = messageCreateEvent.getMessageAuthor().getId();
                    if (messageCreateEvent.getMessageContent().toLowerCase().contains("mystic mine is fair and balanced")) {
                        messageCreateEvent.getChannel().sendMessage(String.format("<@%s> jump off a cliff", authorid));
                    }
                });
                api.addMessageCreateListener(messageCreateEvent -> {
                    Long authorid = messageCreateEvent.getMessageAuthor().getId();
                    Long channelId = messageCreateEvent.getChannel().getId();
                    if (messageCreateEvent.getMessageContent().toLowerCase().contains("shade appear")) {
                        if (channelId.toString().equals("694624769378615296"))
                            messageCreateEvent.getChannel().sendMessage("<@426554216186642434> come here bitch");
                    }
                });
                api.addMessageCreateListener(messageCreateEvent -> {
                   if(messageCreateEvent.getMessageContent().equals("!ping"))
                   {
                       messageCreateEvent.getChannel().sendMessage("pong");
                   }
                });
            } ///Responses
            {
                api.addMessageCreateListener(messageCreateEvent -> {
                    if (messageCreateEvent.getMessageContent().toLowerCase().equalsIgnoreCase("!start")) {
                        String playerId = messageCreateEvent.getMessageAuthor().getIdAsString();
                        String name = messageCreateEvent.getMessageAuthor().getName();
                        boolean isRegistered = false;
                        for (int i = 0; i < playerList.size(); i++) {
                            if(playerList.get(i).getId().equals(playerId))
                            {
                                isRegistered = true;
                            }
                        }
                        if(!isRegistered)
                        {
                            Player newPlayer = new Player(name, playerId, 100); //toDo ???
                            playerList.add(newPlayer);
                            messageCreateEvent.getChannel().sendMessage("Welcome welcome!"); //ToDo
                            User user = messageCreateEvent.getMessage().getUserAuthor().get();
                            Role role = api.getRoleById("695183647375884308").get();
                            Role role2 = api.getRoleById("695322156267208765").get();
                            role.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                            role2.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                        }
                        else {
                            messageCreateEvent.getChannel().sendMessage("You've already started!");
                        }
                    }
                }); //TODO Start message
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessageContent().toLowerCase().equalsIgnoreCase("!players")) {
                        if (messageCreateEvent.getMessage().getChannel().equals(api.getChannelById("694611623100547092"))) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < playerList.size(); i++) {
                                sb.append(playerList.get(i).getName());
                                sb.append(System.lineSeparator());
                            }
                            messageCreateEvent.getChannel().sendMessage(sb.toString());
                        }
                    }
                }); //List of players
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessageContent().toLowerCase().contains("!remove")){
                       String removedUserId = messageCreateEvent.getMessage().getMentionedUsers().get(0).getIdAsString();
                       for(int i=0;i<playerList.size();i++)
                       {
                           if(playerList.get(i).getId().equals(removedUserId))
                           {
                               playerList.remove(playerList.get(i));
                           }
                       }
                    }
                }); //Remove a player
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessageContent().contains("!stats")){
                        StringBuilder sb = new StringBuilder();
                        String targetUserId = messageCreateEvent.getMessage().getMentionedUsers().get(0).getIdAsString();
                        for(int i=0;i<playerList.size();i++)
                        {
                            if(playerList.get(i).getId().equals(targetUserId))
                            {
                                Player targetPlayer = playerList.get(i);
                                sb.append(String.format("Stats of %s", targetPlayer.getName() ));
                                sb.append(System.lineSeparator());
                                sb.append(String.format("Mokeys: %s", targetPlayer.getCoins()));
                                sb.append(System.lineSeparator());
                                sb.append(String.format("Wins: %s / Losses: %s",targetPlayer.getWins(),targetPlayer.getLosses()));
                                sb.append(System.lineSeparator());
                                sb.append(String.format("ELO: %.2f",targetPlayer.getElo()));
                                messageCreateEvent.getChannel().sendMessage(sb.toString());
                                break;
                            }
                        }
                    }
                }); //!stats
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessageContent().contains("!loss"))
                    {
                        Server server = api.getServerById("640960160843563028").get();
                        Role playerRole = api.getRoleById("695183647375884308").get();
                        String winTargetUserId = messageCreateEvent.getMessage().getMentionedUsers().get(0).getIdAsString();
                        if(messageCreateEvent.getMessage().getMentionedUsers().get(0).getRoles(server).contains(playerRole)) {
                            String lossTargetUserId = messageCreateEvent.getMessageAuthor().getIdAsString();
                            if (!winTargetUserId.equals(lossTargetUserId)) {
                                int playerWon = 0;
                                int playerLost = 0;
                                for (int i = 0; i < playerList.size(); i++) {
                                    if (playerList.get(i).getId().equals(winTargetUserId)) {
                                        playerWon = i;
                                    }
                                    if (playerList.get(i).getId().equals(lossTargetUserId)) {
                                        playerLost = i;
                                    }
                                }
                                Player targetWon = playerList.get(playerWon);
                                Player targetLoss = playerList.get(playerLost);
                                double EloChange = 0;
                                if (targetWon.getElo() > targetLoss.getElo()) {
                                    double difference = (targetWon.getElo() - targetLoss.getElo()) / 30;
                                    if (difference > 5) difference = 5;
                                    EloChange = 10 - difference;
                                    targetWon.setCoins(targetWon.getCoins() + (15 - (int) difference));
                                    targetLoss.setCoins(targetLoss.getCoins() + (5 + (int) difference));
                                } else if (targetLoss.getElo() > targetWon.getElo()) {
                                    double difference = targetLoss.getElo() - targetWon.getElo() / 30;
                                    if (difference > 5) difference = 5;
                                    EloChange = 10 + difference;


                                }
                                //--
                                targetWon.setCoins(targetWon.getCoins() + (15 + random.nextInt(4) + 1));
                                targetLoss.setCoins(targetLoss.getCoins() + (5 + random.nextInt(2) + 1));
                                targetWon.setWins(targetWon.getWins() + 1);
                                targetLoss.setLosses(targetLoss.getLosses() + 1);
                                targetWon.setElo(targetWon.getElo() + EloChange + 1);
                                targetLoss.setElo(targetLoss.getElo() - EloChange);
                            } else {
                                messageCreateEvent.getChannel().sendMessage("You can't lose to yourself!");
                            }
                        }else{
                            messageCreateEvent.getChannel().sendMessage("That player isn't registered yet!");
                        }
                    }
                }); //!Loss
                api.addMessageCreateListener(messageCreateEvent -> {
                    String[] command = messageCreateEvent.getMessageContent().split(" ");
                   if(command[0].equals("!award"))
                   {
                       for(int i=0;i<playerList.size();i++)
                       {
                           if(playerList.get(i).equals(messageCreateEvent.getMessageAuthor().getIdAsString()))
                           {
                               playerList.get(i).setCoins(playerList.get(i).getCoins()+Integer.parseInt(command[2]));
                           }
                       }

                   }
                }); //!Award
                //api.addMessageCreateListener(messageCreateEvent -> {
                  // if(messageCreateEvent.getMessageContent().equals("!pack")) //TODO actually make this
                    //{
                       // int card1 = random.nextInt(3)+1;
                      //  int card2 = random.nextInt(3)+1;
                      //  int card3 = random.nextInt(5-4)+4;
                        //StringBuilder sb = new StringBuilder();
                       // for(int i=0;i<playerList.size();i++)
                       // {
                       //     if(playerList.get(i).getId().equals(messageCreateEvent.getMessageAuthor().getIdAsString()))
                      //      {
                     //           playerList.get(i).setCoins(playerList.get(i).getCoins()-20);
                     //       }
                     //   }
                     //   sb.append("------Pack Contents------");
                     //   sb.append(System.lineSeparator());
                     //   switch (card1)
                     //   {
                     //       case 1: sb.append("Common - 1");sb.append(System.lineSeparator());break;
                      //      case 2: sb.append("Common - 2");sb.append(System.lineSeparator());break;
                    //        case 3: sb.append("Common - 3");sb.append(System.lineSeparator());break;
                    //
                    //    }
                     //   switch (card2)
                      //  {
                    //        case 1: sb.append("Common - 1");sb.append(System.lineSeparator());break;
                    //     case 2: sb.append("Common - 2");sb.append(System.lineSeparator());break;
                     //       case 3: sb.append("Common - 3");sb.append(System.lineSeparator());break;
                      //  }
                       // switch (card3)
                        //{
                        //    case 4: sb.append("Rare - 4");sb.append(System.lineSeparator());break;
                         //   case 5: sb.append("Ultra Rare - 5");sb.append(System.lineSeparator());break;
                        //}
                        //    messageCreateEvent.getMessage().getUserAuthor().get().sendMessage(sb.toString());
                    //}
               // }); //TODO Packs, insert set 1 / packs
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!stats"))
                    {
                        StringBuilder sb = new StringBuilder();
                        String targetUserId = messageCreateEvent.getMessageAuthor().getIdAsString();
                        for(int i=0;i<playerList.size();i++)
                        {
                            if(playerList.get(i).getId().equals(targetUserId))
                            {
                                Player targetPlayer = playerList.get(i);
                                sb.append(String.format("Stats of %s", targetPlayer.getName() ));
                                sb.append(System.lineSeparator());
                                sb.append(String.format("Mokeys: %s", targetPlayer.getCoins()));
                                sb.append(System.lineSeparator());
                                sb.append(String.format("Wins: %s / Losses: %s",targetPlayer.getWins(),targetPlayer.getLosses()));
                                sb.append(System.lineSeparator());
                                sb.append(String.format("ELO: %.2f",targetPlayer.getElo()));
                                messageCreateEvent.getChannel().sendMessage(sb.toString());
                                break;
                            }
                        }
                    }
                }); //stats no target
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessage().getContent().equalsIgnoreCase("!test"))
                    {
                        if(messageCreateEvent.getMessageAuthor().getId()%2==0)
                        {
                            messageCreateEvent.getChannel().sendMessage("You are not an idiot! Congratulations!");
                        }else {
                            messageCreateEvent.getChannel().sendMessage("You passed the idiot test! You are a certified idiot! Congratulations!");
                        }
                    }

                }); //!Test
                api.addMessageCreateListener(messageCreateEvent -> {
                   if(messageCreateEvent.getMessageContent().split(" ")[0].equals("!starter"))
                            {
                                Role starter1 = api.getRoleById("695314837772828693").get(); //ToDo Name
                                Role starter2 = api.getRoleById("695314951836926072").get(); //ToDo Name
                                Role new_ = api.getRoleById("695322156267208765").get();
                                Server server = api.getServerById("640960160843563028").get();
                                if(messageCreateEvent.getMessage().getUserAuthor().get().getRoles(server).contains(new_)) {
                                    switch (messageCreateEvent.getMessageContent().split(" ")[1]) {
                                        case "starter1":
                                            starter1.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                                            messageCreateEvent.getMessage().getUserAuthor().get().removeRole(new_);
                                            messageCreateEvent.getChannel().sendMessage("Congratulations! You have received your starter deck!");
                                            break;
                                        case "starter2":
                                            starter2.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                                            messageCreateEvent.getMessage().getUserAuthor().get().removeRole(new_);
                                            messageCreateEvent.getChannel().sendMessage("Congratulations! You have received your starter deck!");
                                            break;
                                        default: {
                                            messageCreateEvent.getChannel().sendMessage("Invalid starter!");
                                        }
                                    }
                                }else{
                                    messageCreateEvent.getChannel().sendMessage("You've already chosen your Starter Deck!");
                                }
                            }
                }); //!Starter
                api.addMessageCreateListener(messageCreateEvent -> {
                       if(messageCreateEvent.getMessageContent().split(" ")[0].contains("!buy")) {
                           Server server = api.getServerById("640960160843563028").get();
                           Role starter1 = api.getRoleById("695314837772828693").get();
                           Role starter2 = api.getRoleById("695314951836926072").get(); //ToDo
                           Role set1 = api.getRoleById("695315001510068324").get();
                           messageCreateEvent.getChannel().sendMessage("yo");
                           switch (messageCreateEvent.getMessageContent().split(" ")[1]) {
                               case "starter1":
                                   if (!messageCreateEvent.getMessage().getUserAuthor().get().getRoles(server).contains(starter1)) {
                                       for (int i = 0; i < playerList.size(); i++) {
                                           if (playerList.get(i).getId().equals(messageCreateEvent.getMessageAuthor().getIdAsString())) {
                                               if (playerList.get(i).getCoins() >= 100) {
                                                   playerList.get(i).setCoins(playerList.get(i).getCoins() - 100);//ToDo
                                                   starter1.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                                                   messageCreateEvent.getChannel().sendMessage("Transaction complete!");
                                               } else {
                                                   messageCreateEvent.getChannel().sendMessage("You don't have enough Mokeys!");
                                               }
                                           }
                                       }
                                   } else {
                                       messageCreateEvent.getChannel().sendMessage("You've already bought this!");
                                   }
                                   break;
                               case "starter2":
                                   if (!messageCreateEvent.getMessage().getUserAuthor().get().getRoles(server).contains(starter2)) {
                                       for (int i = 0; i < playerList.size(); i++) {
                                           if (playerList.get(i).getId().equals(messageCreateEvent.getMessageAuthor().getIdAsString())) {
                                               if (playerList.get(i).getCoins() >= 100) {
                                                   playerList.get(i).setCoins(playerList.get(i).getCoins() - 100);//ToDo
                                                   starter2.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                                                   messageCreateEvent.getChannel().sendMessage("Transaction complete!");
                                               } else {
                                                   messageCreateEvent.getChannel().sendMessage("You don't have enough Mokeys!");
                                               }
                                           }
                                       }
                                   }
                                   break;
                               case "set1":
                                   if (!messageCreateEvent.getMessage().getUserAuthor().get().getRoles(server).contains(set1)) {
                                       for (int i = 0; i < playerList.size(); i++) {
                                           if (playerList.get(i).getId().equals(messageCreateEvent.getMessageAuthor().getIdAsString())) {
                                               if (playerList.get(i).getCoins() >= 100) {
                                                   playerList.get(i).setCoins(playerList.get(i).getCoins() - 100);//ToDo
                                                   set1.addUser(messageCreateEvent.getMessage().getUserAuthor().get());
                                                   messageCreateEvent.getChannel().sendMessage("Transaction complete!");
                                               } else {
                                                   messageCreateEvent.getChannel().sendMessage("You don't have enough Mokeys!");
                                               }
                                           }
                                       }
                                   }
                                   break;
                               default:
                               {
                                   messageCreateEvent.getChannel().sendMessage("Invalid set!");
                               }
                           }
                       }
                }); //!Buy
                api.addMessageCreateListener(messageCreateEvent -> {
                    if(messageCreateEvent.getMessageContent().equalsIgnoreCase("!help"))
                    {
                        messageCreateEvent.getMessage().getUserAuthor().get().sendMessage(
                                "Imagine wanting help lmfaoo"
                        );
                    }
                });

            }
            System.out.println(api.createBotInvite());
        }

}

