package com.yiyiaddon.e.f;

import baritone.api.BaritoneAPI;
import baritone.api.process.IBaritoneProcess;
import baritone.api.process.PathingCommand;
import baritone.api.process.PathingCommandType;
import com.google.gson.JsonObject;
import com.yiyiaddon.e.f.b.d;
import com.yiyiaddon.e.f.c.c;
import com.yiyiaddon.l.f.i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.GameType;

public final class a extends com.yiyiaddon.d.b.a {
   private static final String hu = (String)com.yiyiaddon.m.b.a<"s1neqzo2iyoopw","u2KY3n/t2SMBmzBk0gLllMmebvKhqU1puTJRdjbD",7326988713327091839,1769354547528146549,3537744798598935453,-2989822232034123644>();
   public static final String hv = "killaura";
   private static final String hw = (String)com.yiyiaddon.m.b.a<"s1rlc6b21k7cgk","SHmjxV2sDn3K+rZqdWVr2y0gcPP/JL8seJGIbO4MevY9gMbdL8a/XGAGhPRncvPBnCjJn5TGT5ixDeGzEb4dr5/8O/2pSa06zF4=",4878370800912546794,-5754338894628721809,-6840211184001315767,-2687570891907112589>();
   private static final String hx = (String)com.yiyiaddon.m.b.a<"s1byt1arvbs895","MDRvAa3ttomCzKp0vqlaensz7RKSXCQnFkRXHrLCUMuxddNpzuMhf1Kx3jw8AM+9FBae/2KwvZ5iBzCM+peOvqt5mF1UYg==",-9183112012128122830,7333867641588506361,8401506516582602143,-5484280461540878283>();
   private static final String hy = (String)com.yiyiaddon.m.b.a<"s2v2k54d19j2tc","LBK97D2xZB3DbHgyyavziRDR4halOQqfF/A0sbxP3XaqPXhDpOIq6pH1lw6qhCstln83EjedaGU0RRmDw/Kwynl86PssJJk3OoZF7NyQ",-7768993954306502658,-2421676564862102993,3030466140285720799,-7331825614926092032>();
   private static final String hz = (String)com.yiyiaddon.m.b.a<"s2lat9k277dd6w","IIBp+rUrho6lGP8CXZ8SrGZ7D9N2qSa92dyIKvcsuKT34mum5SSyjosoeflAy7Nnwk/IJA0aMbtVyxMH0JYmV/bKc8t7wLTxlvl25Q==",6036138066964035769,-7770166677448014128,2911557615838083651,-1460608602918031414>();
   private static final String hA = (String)com.yiyiaddon.m.b.a<"s7nevunyued33","i53NwPyM0kV+aR9AXklT/LM+CBhZIE+f+Zz4MpimnO9EWgg+qZPs8MKq8h3HZqQI0ZCP2SIwiwsdcqaCC7euP0tnI85+nw==",-3958305245081673359,1988122361009986566,3764810099364584299,-810561305298693969>();
   private static final String hB = (String)com.yiyiaddon.m.b.a<"s2a4k0mdffxj2i","jwM6Zp2pImsp6VoOICHQ2ot+i0J5gEIX217U79+vlfYqhnpLHRgyc8Osueag8ONhUKJARSLCwwg=",-518751321629354119,4001992118286084339,6032874483571164368,-6213986205498911431>();
   private static final String hC = (String)com.yiyiaddon.m.b.a<"s1lzk2ejv0ozq6","cYlkHr5n5Znpy955D8UcHieIEwByB6BUnQKMwXgJc4HOH2Hga/HW2mHOyvCSrJW13bwDVgjn6SypSCDESa3j1VW3xpXU3ziOTMs=",9025601860202036078,742870934522134447,-8276922583748844513,-566919735465588280>();
   private static final String hD = (String)com.yiyiaddon.m.b.a<"se0kgrejaqws8","2FzYZFZtN0UBBKN/mKmZz6kiCl2cPDWbk/Eajyn5wyMPEcep6HbBGUb1O+52tNdwdP4G9p0pLRuGow0Go3M=",3174269735401261458,-803065019044724072,-2027234131961692538,1914090664209664062>();
   private static final float am = 15.0F;
   private static final boolean aM = false;
   private final Minecraft v = Minecraft.getInstance();
   private final com.yiyiaddon.e.f.a.a a = new com.yiyiaddon.e.f.a.a();
   private final d a = new d(this.a);
   private final com.yiyiaddon.e.f.a.b a = new com.yiyiaddon.e.f.a.b();
   private final List<Entity> P = new ArrayList<>();
   private int dm;
   private int dn;
   private boolean aN;
   private boolean aO;
   private boolean Q;
   private int do = -1;
   private boolean aP;
   private boolean aQ;

   public com.yiyiaddon.e.f.a.a a() {
      return this.a;
   }

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s242tc9wtzhl3g","/u+JPbmjgU5IOMrbQszx3S9KJcWxpIguD/CcfdnldxOagt1S6Gs7l5hRnPs=",2271703836137343377,5593956999167690061,2285354618763969156,-3483702041718597481>(),
         (String)com.yiyiaddon.m.b.a<"s3ppkt7ekm087e","dDKuIapvR55Y+sfVCkl/kBUnjSTY5BXau21z0zt2LYK6I1VC",1684465880791171672,-2220520776600075017,2268962694307902057,7313793433904374448>(),
         (String)com.yiyiaddon.m.b.a<"sjq2f35rh2k0v","lzGWvD5uC+ZgYMAk9x2nLXNN8AZPiFnWhPOWzkGDKsvPB7S04HShtQ==",4411606172126509539,-2071267467426255263,-3006854714991523780,-2191555616395724560>(),
         (String)com.yiyiaddon.m.b.a<"s39jfqmz38x9qd","7U0MPywoeLUOubI/aLrekgr2NfmOn02rlRo0FDqprz7U1cDNImu2DLypHDVNUYNp",2329696562347126957,4311250197991964845,-5722151909597254204,-1178358628609034921>()
      );
      this.bs();
   }

   @Override
   public int i() {
      return 10;
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s1neqzo2iyoopw","u2KY3n/t2SMBmzBk0gLllMmebvKhqU1puTJRdjbD",7326988713327091839,1769354547528146549,3537744798598935453,-2989822232034123644>();
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   @Override
   public List<String> f() {
      return List.of();
   }

   @Override
   public i a() {
      return new c(this);
   }

   @Override
   protected void m() {
      this.do = -1;
      this.Q = false;
      this.aO = false;
      this.aN = false;
      this.aP = false;
      this.dm = 0;
      this.dn = 0;
      this.P.clear();
      this.a.f();
   }

   @Override
   protected void n() {
      this.P.clear();
      this.bp();
      this.aP = false;
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.PACKET_SEND, com.yiyiaddon.d.a.c.PACKET_RECEIVE);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s182u03qldb0cj","Pffep7ym5mFyYJtxZskFgQ0hxxvWnOe51PeVJYMtN9E=",3309703100406174,3400951751010060154,2369048602867337545,8398836905331739451>()) {
            case -1308698680:
               return;
            default:
               throw null;
         }
      } else {
         switch (var1.a()) {
            case PACKET_SEND:
               if (ServerboundSetCarriedItemPacket.class.getName().equals(var1.l())) {
                  switch ((int)com.yiyiaddon.m.b.a<"sc41ctwtghh6","0BO3VLvKCEXaXK2Q0mC9kp/UogccYJUqmhklRTNrnhI=",5534237412727265677,7735908984595303857,2292946727435057854,-9216575199490890536>()) {
                     case -730845337:
                        this.dm = this.a.dv;
                        switch ((int)com.yiyiaddon.m.b.a<"sza3fazrn0n15","7WwPXPAaNCazkpr6KYNm9cAH3QhuAul5ksUBqkjBAPQ=",-1320630287151875251,-636535172453038366,-5090217082983053772,-2704588089374360648>()) {
                           case -497400504:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case PACKET_RECEIVE:
               if (this.a.u(var1.l())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3iimotsdliogx","pSs/QEKeCWpiFGPoQkPc+e1p4g6OdHX1+icoc4CN5Ys=",566614354865090769,8002227727161198346,-6944118721441583686,4474909643512199912>()) {
                     case -2056994182:
                        this.a.bt();
                        switch ((int)com.yiyiaddon.m.b.a<"s1oj2sgl24a5mp","OWD4c33tz8hzh235zQAOzAFc/ecDtw2J/rLRb8Tot88=",-6092075460056535058,7998162444449287508,-8157592150106123039,-2342008826942982688>()) {
                           case -2136295722:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
         }
      }
   }

   @Override
   public void b(Minecraft var1) {
      if (this.v.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3safz9qmwdo81","epvwioKFQJo3hr4p86PGtcyO6kNY2f4x119aSsydvUY=",6954612200602208078,738340508676042501,291039991253035475,-5149087464443751329>()) {
            case 818796271:
               if (this.v.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2t3wgoma3474d","NMWnAw0FYT/Kdct5XRROm3xwqM8QSK9Y9m46aXrmxUg=",-3108514082292905962,-4792447475077308921,6359432190548691020,-2731976519956887890>()) {
                     case 473454909:
                        if (this.v.gameMode != null) {
                           if (this.v.player.isAlive()) {
                              label190:
                              switch ((int)com.yiyiaddon.m.b.a<"s3ljsnzwn3iros","K3fAzYisqG0mG1czVFwpTN9HTcDtL7a/1O3eOnUhkk0=",6871823806755083184,7739341456761558473,-8337709519346765672,-682501406928830898>()) {
                                 case -1820490449:
                                    if (this.a() != GameType.SPECTATOR) {
                                       label251: {
                                          if (this.a.bb) {
                                             label186:
                                             switch ((int)com.yiyiaddon.m.b.a<"s34l41ls9oy3kn","Nqm/UYy1erDSS32wkzaj6uSkg1M+Skz8O5RT/SFnUaE=",-723865804800874755,-2682305251353164627,2385556254669748013,-1466511958527273815>()) {
                                                case 1670503050:
                                                   if (this.v.gameMode.isDestroying()) {
                                                      break label251;
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s21tui0v3tlgyc","B3s6SjO76+IcMt5oL8IEkgeAN6QaZ0l0YTe5EcjAo5g=",8152197887094896945,7936070214909796156,-509725143761637108,-8625677439048781034>()) {
                                                      case 542416796:
                                                         if (this.v.player.isUsingItem()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"sbilrw2i95zbg","hoHD2O/n9CQ/AlECkEc/NsAZcWMCe7ngJQNiH3tkbMk=",-657731947603078507,780326169462538858,-1108598988371656761,7261272631414786486>()) {
                                                               case 1313876595:
                                                                  break label251;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break label186;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.a.aU) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1y2fl7sgz2jj","FjALdvi1UqYsZPJWvmxiZKy0j/Et4i6RBeEXa5YsZLQ=",-2044836867876063470,-2762921119505090271,5002936296124928072,-6789323459955009979>()) {
                                                case 775978127:
                                                   if (!this.v.options.keyAttack.isDown()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1btq30m0aucfj","qZPFILxYEItAbCgSoP65CIGEArUCWft/yXhiUCAK5X0=",716427442473828108,1434790582242950110,5312815572219914982,-1143193086280833902>()) {
                                                         case 1621694939:
                                                            this.bp();
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.a.e() >= 1.0F) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2r736mih69xih","4pHY6kPokKXC8GAveJGb7xkSrxLHsP0A8JMmJ9PQGFk=",7898402317827704911,8942727122752294925,1225366414552567694,-419543514799251586>()) {
                                                case 1488671927:
                                                   if (this.a.ba) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2kttf18onm1en","Z8WM8TDR/WW1RUkPh+6jrusTfG0IA5Mec2JMVB+Ye3c=",7805411823026605071,-5399969726985379784,-6356242584565083969,3232482204474419355>()) {
                                                         case -1941364961:
                                                            this.bp();
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.a.bc) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1a3nxlcpl6rfk","Yff4x7NqKRrD93Et0FyjsHWtWhYRoUskA04m5UvPgV0=",521943497321844598,2616250156302705324,-2231603152724364265,113847748585015195>()) {
                                                case -750607355:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          label253: {
                                             if (this.a.aV) {
                                                label172:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1nvr7yvlfcl58","iEZBOWfQ7hT3msEsUYas9PZsP8/Q0IwCFp036yAmQW8=",-2539015088830833496,-480976067513358896,3791081835306835358,406820980187736771>()) {
                                                   case -152566351:
                                                      Entity var2 = this.v.crosshairPickEntity;
                                                      if (var2 == null) {
                                                         break label253;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s3aw4g9z1gah52","GG/e4CzFGZ8HXzNlBEY/nKZ9rLwxs6+600esGfkq5NU=",5393174894900945754,-5328859791846713767,-6498162635319068567,-1357142693240442109>()) {
                                                         case 1114652125:
                                                            if (!this.a.a(var2)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s16s0bwp3jatl5","DIyho8bB6JQStQaw79ZeHKDUhJUy+w16onguY1xpD4g=",5616592033010799826,-2602380796475825469,-8539121869479314858,140937843663873564>()) {
                                                                  case 887999701:
                                                                     break label253;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            this.P.clear();
                                                            this.P.add(var2);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s5r24pwl4dxme","iygzGyTOIeQU+MujDf7Ax+NJ/L/Uhb4a0StS29d/i08=",-6669316081773707182,7613789704704998304,-522886577664070825,-3677220314562146810>()) {
                                                               case -593265956:
                                                                  break label172;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                this.P.clear();
                                                this.a.a(this.P, this.a.a, this.a.dt);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2kilkujn6a44r","oxoH0n1dC2LtfVNvU4Vrvbt/KFmet6Es0lbQewXgcBg=",1594442999963581483,-5783722021961968451,-7034343464576829565,-8316458656371494898>()) {
                                                   case -265864667:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.P.isEmpty()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s164sr75zlogmz","DdC4W5R3vz7VcgC/tRSDtrKzro+zRmd1P1S2ysEeyuo=",-4993462497247392355,-1843349773951217080,-8941430367168521110,571505352858646922>()) {
                                                   case 263454583:
                                                      this.bp();
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             Entity var5 = this.P.get(0);
                                             if (this.a.aS) {
                                                label166:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3ke4ld1wvi77r","PdV3Y/CupHmlzEJxMjJhKmuISc3X1a9GMheh5i/F2do=",7787024527873418704,2548758487847195922,-2589554952953448830,5981782082761197301>()) {
                                                   case -1010206230:
                                                      int var3 = this.v.player.getInventory().getSelectedSlot();
                                                      if (this.a.a == com.yiyiaddon.e.f.a.a.a.WEAPONS) {
                                                         label163:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1qpj717p1p442","sowijkHF5FATVxmZwUDGvTtJrn4hdrKtTLLy26tpu94=",5361589093712134385,918991734287731933,-4095403946301385856,7510023153371822786>()) {
                                                            case -22012753:
                                                               var3 = this.a(this::k);
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2bpzr465r7lsu","t2hrp1QyXXORydcLnZKtLrRkERPhXZILTOKRLaVflLQ=",9158943152337655084,-7254044850896655724,1242545449776575633,2980434348760778557>()) {
                                                                  case -210888370:
                                                                     break label163;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (this.ay()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3982enitimhv9","dqeKditGQtTCfwInBR1zM4F0TuP5tlQi8XcqSPmAJm0=",-4708130285097227228,6509037577797188125,-3170463158553871347,5773456935878437907>()) {
                                                            case 155869945:
                                                               int var4 = this.a(var0 -> var0.getItem() instanceof AxeItem);
                                                               if (var4 >= 0) {
                                                                  label156:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sw2uaw6twckjf","msDuDbBefEzzIlTMEwcWR+nk6r4FpBSAaOEfAOuxufg=",-7291161768321029784,-6278280599443782059,4042933571932619570,-8346910003767023998>()) {
                                                                     case 1968341939:
                                                                        var3 = var4;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3reujrrnpoi73","NCmWbKWpxWCn6N8Id8ARiHYkLncvMj8GrtLlr39EyQI=",-8697799533182425706,-7691727560382985768,8166936870703912582,2397005066079985610>()) {
                                                                           case 1147399721:
                                                                              break label156;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (!this.Q) {
                                                         label151:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1sqlk8yrl5pdt","iUoIur2TsZcEpR7E41dwky+g8TEDmsEILDrz7TjAIJo=",7572739177748895823,6391513517898227197,8281921758986135121,-5957007117876997005>()) {
                                                            case 1777395486:
                                                               this.do = this.v.player.getInventory().getSelectedSlot();
                                                               this.Q = true;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2l6z0tjkjd8vd","Yji/7W+hRqlmn9sjivktPCxGE1dT3AA/PpfLIOkw3TE=",-7181326932553375511,-2859531759370926584,-8802721748758930252,-4040597611553589527>()) {
                                                                  case 419746708:
                                                                     break label151;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      this.f(var3);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1zc7npvkdp1ob","cOHEwDzd8Kb+yOOyE4h2MrBciWSz0kWLKsytiSuvjgo=",-1148212869801385697,-2898818553420967300,-8226201629451250818,533152868577728483>()) {
                                                         case 2051105948:
                                                            break label166;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (!this.k(this.v.player.getMainHandItem())) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s311jhdvxr9uwv","MeaGDJcVrsiJio6AosoIB4tw1am0Qz1fUF+ofEJZVEc=",2836529522557144437,602718552881896753,2377410485807866104,-7843644722968856240>()) {
                                                   case 742941601:
                                                      this.bp();
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.aO = true;
                                             if (this.a.c == com.yiyiaddon.e.f.a.a.c.ALWAYS) {
                                                label145:
                                                switch ((int)com.yiyiaddon.m.b.a<"s10e0z1utkerze","BnhMh+EY2L6dcKvAbQofwlbaMAKINYuMx1ornP4D3Wk=",-6474279973417491110,-7707076013638877742,8754689091968517022,2761488428345159812>()) {
                                                   case -2011530881:
                                                      this.b(var5);
                                                      switch ((int)com.yiyiaddon.m.b.a<"slu4a0aqoobki","M3bILExPbN0y91QrQe6q92+m0eISjMelM9Uh35/c0QM=",8479354082638400278,227165318066763960,8176520521767704933,-8885083964950587186>()) {
                                                         case -1080535294:
                                                            break label145;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.a.aW) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s146j80p9vxpuj","MgXU2Jwr6AQNmZLt027Unj1WsHj+ir/VcmZdbucqr58=",3567070335921079874,-3984185209507479382,-781374561516455847,-7125841517705569091>()) {
                                                   case 1564516002:
                                                      if (this.w()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"sbmntop67vobr","h4dt7zySNm6uI01jYOVYc3BdckQv66Md8Tia1dc9BZc=",9114878236406343761,6139670558848997808,-6520861882379304185,-5117419991190323258>()) {
                                                            case -190879438:
                                                               if (!this.aN) {
                                                                  label137:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3o7vsal2r9sr","rDW5zNa4H93UoQ3K9itYrl6hPza20DijlYvDcN2bK5Q=",1550415632959581486,7998848706772910572,-2617708317702073548,-1532216893974929502>()) {
                                                                     case -1901664331:
                                                                        this.bq();
                                                                        this.aN = true;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s14argul9zggb7","ygQ5/VJdv9N2+Y4xmHF8GOAnm+TpxsZyEtDuEDlBq9w=",-3999155606897383170,-7479330583164283334,2350697850098065902,-4260129606918986939>()) {
                                                                           case 764723385:
                                                                              break label137;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.az()) {
                                                label134:
                                                switch ((int)com.yiyiaddon.m.b.a<"szsg0m1avztss","qsBI6ybJClA/DuZ68jaiiyI8u1N8FSk9KHVMYxf59Ys=",2050194386910427939,-7949703927404294429,2448375591913519161,-2985505804529554126>()) {
                                                   case -2137598805:
                                                      Iterator var6 = this.P.iterator();
                                                      switch ((int)com.yiyiaddon.m.b.a<"spnw5sf83p43g","88bAEFywNCXHv1WnUa4cQ5OFUXwCgJVERmgj6LoWAKA=",1439003069757486050,3625899474725391720,6791476257205181522,2904342983040555547>()) {
                                                         case -967537512:
                                                            while (var6.hasNext()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3oox892cjtz0z","xZeLzBonWLjWSmWQbWba/QOf5onzg4D/L0X6jsk7mh8=",4517896550392118441,-6304581496660995069,-6189777660860663624,8711039612858082735>()) {
                                                                  case 1017342803:
                                                                     Entity var7 = (Entity)var6.next();
                                                                     this.a(var7);
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2c8lfzzizig4f","baeXyR5w4YG3mvapecYTw5mMJFx6XHn1yxmOHuLjgms=",-1989029475055626257,1138884105986835520,-3020735127468310548,-3395413972794328821>()) {
                                                                        case 238949889:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break label134;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             return;
                                          }

                                          this.bp();
                                          return;
                                       }

                                       this.bp();
                                       return;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"srzyvuou2dkgp","wkZhDjjHgIwIT1+VErbWO+PWwS8ls+T4iVKGfWzK//Q=",-2073783358387513521,427249947678336262,-4663721437028789965,2783813667242324995>()) {
                                       case 1204170811:
                                          break label190;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.bp();
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3rg8o59j1bz22","mewnwY9F0U6PVd3RBt5JQ8Xi1LLa/O/3Ob0IY9teKMo=",5086277184813190711,8552606875814147929,-8798944974954551269,70497813915864696>()) {
                           case -1180486292:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }
   }

   private void bp() {
      if (!this.aO) {
         switch ((int)com.yiyiaddon.m.b.a<"s20iw7x3sesi12","56ccs0vmegfwT73Fmqc631z0YgwVkCddRIEXQv7mrmE=",-3595528780481883061,3463388868728288302,557361621395305983,-4454963636955534223>()) {
            case -1887189865:
               return;
            default:
               throw null;
         }
      } else {
         this.aO = false;
         if (this.aN) {
            label33:
            switch ((int)com.yiyiaddon.m.b.a<"s12sl0ow2e9ykf","ZYDl6KUVFkIDuNLy81ynDOJrp7ImQFuizX5+VMoppuQ=",-8234657237470524979,3265950643569499097,-2811351394059414543,3987614125556504701>()) {
               case 77159784:
                  this.br();
                  this.aN = false;
                  switch ((int)com.yiyiaddon.m.b.a<"s2pcgzdqapb66u","8iXcQ30qG1UKMMQEt3uLK8IBE7hepL6N4vNG/jFfowQ=",-7668261567670964989,-2370800456978045705,-4391575522536975784,9039359364769148985>()) {
                     case 1289207939:
                        break label33;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.a.aT) {
            switch ((int)com.yiyiaddon.m.b.a<"s2chjhioh4j0ve","/MDqti126FZRQInX4YPPWCAAq5D+dM1BcJCvoU00TUA=",1261447955431083803,4378541247604553181,4682854890303548703,4579276259362052045>()) {
               case 230762659:
                  if (this.Q) {
                     switch ((int)com.yiyiaddon.m.b.a<"slk6fy087y15s","qoc8rAv3kIkLEyt8eVaZchw9i/ifTSCqOqAOJaHrO3Q=",-7955951046834459611,4638570581014572140,5748245937071340227,-4315124653768425322>()) {
                        case -884825814:
                           this.f(this.do);
                           this.Q = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s20vyplk4oj6u8","ixlox9w+0WhsdD3AypsOZyO8FhF5dQPTomU2azqd7VU=",4871732717154379937,2847099960375258803,4562756028205796073,7471363303919778582>()) {
                              case -353926040:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean ay() {
      Iterator var1 = this.P.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2wvqxfyl85d8n","Z31TljxxIqAmE9wqQ/M3/T2F96MN6kHcX6II+aV2gqg=",-5710454541506295218,-6797874878280314657,-6946805057812858425,-3286021278069351078>()) {
         case -1596764668:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1740bvexadnwh","NnLPxGKz4p0w6YV77UKXoD7RSDE4sqTl4dnPO/2F0hY=",-3211454786379901237,8895090189060007755,7033723935248846172,2696072946428545525>()) {
                  case 836764681:
                     Entity var2 = (Entity)var1.next();
                     if (var2 instanceof Player) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ch7fgmkqbjca","qOTDPEdv3jdOq6oyfe9swp/KpbDjLBgZi+PNlxmyyNc=",8866085027688332437,1352509860171720993,4942591061343005202,3987820702503897090>()) {
                           case -1297667843:
                              Player var3 = (Player)var2;
                              if (var3.isBlocking()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1sa4xs6vr8pa0","AE396MWiL3UediRlPATifws90ph19ne4xCOfIXwbSAI=",7902633281840223700,-1480355875160597968,-6377077329160674493,7932731767527859629>()) {
                                    case 1455097626:
                                       if (this.a.a == com.yiyiaddon.e.f.a.a.d.BREAK) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1mkqmofqgajyo","KsVgoCu2rNp+zB16b+jBtlURZwQTeLzgfgcP48tIyA8=",-4685182878922992594,3463802403348718835,-6769118074713733018,-8136466426701551965>()) {
                                             case -574292036:
                                                return true;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3ef374zes9p3f","0vZR8uSyyT1tWrrq76f5X8hylQEUeG3J9lPXL1Ldqw4=",2030439885139664502,3479782780968790122,4850838866301194012,5199623706668552867>()) {
                        case 1349303425:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return false;
         default:
            throw null;
      }
   }

   private boolean k(ItemStack var1) {
      if (this.ay()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tm13zgg3mwjl","Yi6ZHwtUymdyZ2rk5OvkDGl08koRigOc+EarqC5kVVQ=",7906946855958903123,-1895747347367958449,-439532556989401384,4160863816395243491>()) {
            case 2097726464:
               return var1.getItem() instanceof AxeItem;
            default:
               throw null;
         }
      } else if (this.a.a == com.yiyiaddon.e.f.a.a.a.ALL) {
         switch ((int)com.yiyiaddon.m.b.a<"s25eb5ixcxochv","X+oFL3Hv0HxrwM/WInKlbS5wSr4+D+3b5/nJ9lkrd/s=",-3874672390996729373,4997662855085082391,-5011015616797165684,1021053370399156028>()) {
            case 479291848:
               return true;
            default:
               throw null;
         }
      } else {
         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s1rlc6b21k7cgk","SHmjxV2sDn3K+rZqdWVr2y0gcPP/JL8seJGIbO4MevY9gMbdL8a/XGAGhPRncvPBnCjJn5TGT5ixDeGzEb4dr5/8O/2pSa06zF4=",4878370800912546794,-5754338894628721809,-6840211184001315767,-2687570891907112589>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s2o031eiept9gs","1ID8bquQIGr7Rbh9M47q674TZjJZOWC8f2g4P2z+/bs=",4764354604271902870,6783586109141381555,1148835072814840286,-8699995600896351714>()) {
               case -203008313:
                  if (var1.is(ItemTags.SWORDS)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3po5kssdhhwkb","bI0vJdzdcYUopkC8ytSanOqHE+j6bNn6DtzYsQivkpU=",6498300966044014992,417562907194002372,7031341346294933131,-6374210581406593455>()) {
                        case -990522020:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s1byt1arvbs895","MDRvAa3ttomCzKp0vqlaensz7RKSXCQnFkRXHrLCUMuxddNpzuMhf1Kx3jw8AM+9FBae/2KwvZ5iBzCM+peOvqt5mF1UYg==",-9183112012128122830,7333867641588506361,8401506516582602143,-5484280461540878283>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qxi214x7ym24","sUnN9f2I1fJnn3/6DkkcJxgBuKunQmeKcr36cWqMncM=",2815998904686579445,-4528773289078130050,-8260896745244236907,-3991139940094923895>()) {
               case 822114714:
                  if (var1.is(ItemTags.AXES)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sz0dkyokgg8pi","m+554RYY8i/AYzKKOgbKhu9wuI5J/2KjxOtM/5nIpXI=",-3128205242370871886,-5855638257087026436,-3406961155711174183,8707943761841698357>()) {
                        case -235656221:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s2v2k54d19j2tc","LBK97D2xZB3DbHgyyavziRDR4halOQqfF/A0sbxP3XaqPXhDpOIq6pH1lw6qhCstln83EjedaGU0RRmDw/Kwynl86PssJJk3OoZF7NyQ",-7768993954306502658,-2421676564862102993,3030466140285720799,-7331825614926092032>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s3hh6zlhm8urne","4Kgp/pFFjRCksjttlzlfD1WHiVU12uE2vyHZd7DtobM=",-505914436016723354,1626814779724413644,2596620867096783079,7689599665607498409>()) {
               case 1520811018:
                  if (var1.is(ItemTags.PICKAXES)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3k9iy7h6h33p2","8Kv1EM1+bVqNa7/C0+IJct3Ecd01jrZJgI/fli0dAsI=",-761478230117359178,-3761156557051865934,767643506482420789,7644327894236738862>()) {
                        case -1373565153:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s2lat9k277dd6w","IIBp+rUrho6lGP8CXZ8SrGZ7D9N2qSa92dyIKvcsuKT34mum5SSyjosoeflAy7Nnwk/IJA0aMbtVyxMH0JYmV/bKc8t7wLTxlvl25Q==",6036138066964035769,-7770166677448014128,2911557615838083651,-1460608602918031414>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s152sz5ifdadvm","O1gT0TIG07sgsrlaYom9pgBYWJ5e5onjud8LDULHytY=",5064859861408473791,-94908848214941731,-6011145225009887154,-414102415942194332>()) {
               case 1932258702:
                  if (var1.is(ItemTags.SHOVELS)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2fzt9gk88x3pc","M10bwXVdclq80sDI9GAulKlZzw1WeX0tZEWImaTNvR8=",2830070875592415645,-7442577193933522805,-5037415799866015987,3600997041191193498>()) {
                        case 902402732:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s7nevunyued33","i53NwPyM0kV+aR9AXklT/LM+CBhZIE+f+Zz4MpimnO9EWgg+qZPs8MKq8h3HZqQI0ZCP2SIwiwsdcqaCC7euP0tnI85+nw==",-3958305245081673359,1988122361009986566,3764810099364584299,-810561305298693969>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s18yjs4wliftd7","9K8qjU93AhehGQtUi9iv7mrX+zAdrziDkOdTqFGs0J4=",-5973923388160227911,-3324906131695193853,4114824580930971477,-1657011092217705619>()) {
               case -1833382058:
                  if (var1.is(ItemTags.HOES)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2hbid5at8qpnq","sDbcWxzOKcMsYcqjJBdJUeo9YBK3+Ep4poxtPauXptQ=",-7075616629819662461,-4459611471609450680,1381121418837369148,-7355693091445490219>()) {
                        case 613583233:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s2a4k0mdffxj2i","jwM6Zp2pImsp6VoOICHQ2ot+i0J5gEIX217U79+vlfYqhnpLHRgyc8Osueag8ONhUKJARSLCwwg=",-518751321629354119,4001992118286084339,6032874483571164368,-6213986205498911431>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s21kaywcgs1a01","eM2PhYgua/ycFvmFb8YuaJXRsrmzlHeTS9scJ6J7V54=",-7519305462924663671,3101377378668709809,947797856195810482,8097632546316161697>()) {
               case 1343836986:
                  if (var1.getItem() instanceof MaceItem) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3quyog1irwzze","T4Nu+ielIWsIbNKsbGvK+ZViuLNLoquVdOpuIKiYn5c=",7938737318211896257,-1536693714463365145,-1498938617236523288,-5895750331479017920>()) {
                        case -559527953:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"s1lzk2ejv0ozq6","cYlkHr5n5Znpy955D8UcHieIEwByB6BUnQKMwXgJc4HOH2Hga/HW2mHOyvCSrJW13bwDVgjn6SypSCDESa3j1VW3xpXU3ziOTMs=",9025601860202036078,742870934522134447,-8276922583748844513,-566919735465588280>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s3smbli1bthqm","osMrw0n8kf0p1nsmulWYX4pk2fvd3GxNGYgdkifTR7M=",-2268639812919742524,-284162335401949405,-3798332010887535703,9055312495763818660>()) {
               case -554572928:
                  if (var1.is(ItemTags.SPEARS)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3rsdxyl4asnjf","yKahZiws9M/avWGd/I1HRmthNOzNH5FztElOf+AOwow=",2226660221619236938,505005121119333187,889237571431060765,4299766792063514857>()) {
                        case -1348445803:
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a
            .w(
               (String)com.yiyiaddon.m.b.a<"se0kgrejaqws8","2FzYZFZtN0UBBKN/mKmZz6kiCl2cPDWbk/Eajyn5wyMPEcep6HbBGUb1O+52tNdwdP4G9p0pLRuGow0Go3M=",3174269735401261458,-803065019044724072,-2027234131961692538,1914090664209664062>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s2uf9klhxi3h8l","abB0sUPCgxICh+ppJ3D1hToA/aeAgEiy9Px8LE36m1I=",-7642953473632733016,5891746098425422029,7664045431044564522,7126032467529803140>()) {
               case -1317492924:
                  if (var1.getItem() instanceof TridentItem) {
                     switch ((int)com.yiyiaddon.m.b.a<"se9qejncbh778","HFRWMP1e4USaSSVhE6H+pr43hITafByWgOyiZxAPVsI=",-3622645866982802197,-6904699035366175123,7974585955937644378,-2884264331261700073>()) {
                        case -256146323:
                           switch ((int)com.yiyiaddon.m.b.a<"s2cl9bf88mvzcx","LabsZr+wTCDLDNPTA/WXVrPaOFlWXabh0HUkudLxTu8=",-4593307960207845834,-1121284522144604406,2379289063577908455,-1318046720092750806>()) {
                              case 264801316:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s28ypqy3ymhcu0","hiIiAiUzh2Mp0eWCcnl86+wXZMGTgLaJgxymQQyXP7A=",-5012650341449121417,-1477498490388922581,-2077103390829669931,5350259709330602599>()) {
            case -2088252695:
               return false;
            default:
               throw null;
         }
      }
   }

   private boolean az() {
      if (this.dm > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1h1lxndbz3soj","tAjJweqL3lUPM02nYMgEda+0wF1UOXBMs5HVNLmPPs8=",3033965694604156796,9045911994544941754,1497401089789655248,4984278609106884321>()) {
            case 56529878:
               this.dm--;
               return false;
            default:
               throw null;
         }
      } else {
         float var10000;
         if (this.a.be) {
            label58:
            switch ((int)com.yiyiaddon.m.b.a<"s30111qyzc1ozt","UM7EhvTRF/+KCq1pG0+10fq0ME49n8Y8vk/TMWFcofM=",-2542128003636094622,-1167310065706962047,1197260044110598658,8927042002965793432>()) {
               case 193934080:
                  var10000 = this.a.du;
                  switch ((int)com.yiyiaddon.m.b.a<"s19z6dnjzy7i0d","uEKFUYrr+1ywe5QOuJ7qncKdU6/zxeqANF4ozuFcm4Y=",5703440540736294890,-7996081892228266952,-3789691281798206368,-7197830641677973392>()) {
                     case -284741813:
                        break label58;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 0.5F;
            switch ((int)com.yiyiaddon.m.b.a<"s2j2ozb6o9ofgb","kmWXqtCaS8Zw0FG2KMHHxk0agvfeEc784hZ/lKrAnNk=",5343185966346102528,-2174308837870462675,-4865672742539581437,5701547116371887611>()) {
               case 2143395499:
                  break;
               default:
                  throw null;
            }
         }

         float var1 = var10000;
         if (this.a.bd) {
            label53:
            switch ((int)com.yiyiaddon.m.b.a<"slxtbel1neyn5","uucai8vD9AnTa3/jSAnFzOeoTG4W2fJnvbHBikR/p4A=",-4816915179838853747,4307471717031080595,-686216414117960288,5200263125838911251>()) {
               case -771886523:
                  var1 /= this.a.a() / 20.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"syap5uem3orir","p/YTPqwyJnnva1ka1OuGIiqsTkfLWNNbKWCOJ/ihN2w=",-5005991371894754065,1915427469134696222,5373605412232085231,-5927417612769080952>()) {
                     case -308868874:
                        break label53;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.a.be) {
            switch ((int)com.yiyiaddon.m.b.a<"snnrx82cb6n7r","G6k/5Fbuu38ufcVDxWnmR6zSoGl5AGvytR7GBDx98V0=",-8112158286209825104,-4846722189264194154,2942388825672563756,3470395749590666201>()) {
               case -1039718086:
                  if (this.dn < var1) {
                     switch ((int)com.yiyiaddon.m.b.a<"sahi3cddxefd5","kbRqy/DSbTWyKLnC//5alg9ivxvXycRVkO1cQKXJu/E=",-2794329926744825183,1731734436296590098,5313454231984351735,-6829574760100021132>()) {
                        case -1241831587:
                           this.dn++;
                           return false;
                        default:
                           throw null;
                     }
                  }

                  return true;
               default:
                  throw null;
            }
         } else if (this.v.player.getAttackStrengthScale(var1) >= 1.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s2sel8r0f0pg00","rMSNh75LXXU3B7OeQoZ3w2jBl36Z3HD2JAXAuV8w1KM=",-3059685971106101599,-6483237709022339360,2443968873442931671,876377768851564224>()) {
               case -697583929:
                  switch ((int)com.yiyiaddon.m.b.a<"s3hgdikwzbe2vw","yBtkMUsEXCwcghvugs9Y9UDYrVCWgBFluP/iYR2gu+w=",2768365303936296082,-8594428156203195074,7497723391490728526,-816333726618001487>()) {
                     case 23436587:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s297ldr2wpmrub","iddCloTYvb/lTEwpXkGWMZclgL/FwxjyTQwrelqbTG8=",9220401362700894323,-206856032799448876,1662862804822809853,-8742820602370434251>()) {
               case -875639909:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   private void a(Entity var1) {
      if (this.a.c == com.yiyiaddon.e.f.a.a.c.ON_HIT) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"svrudk919bapd","VVv6UWgtvyUkkIHUi8sGN8nv7i9Qg5LTIe3F8g8FeZE=",4688259223495242183,-7479294960625353040,3022228213890118384,-2547938419414402929>()) {
            case 672164468:
               this.b(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s1wqaz54nlvsnw","HuwQ1wsHz8h6sVGBg+KLapjwDQ5g/Fm35Y9NLjQMUxU=",5994005574852388939,-8819045359814570930,-2764994440896864132,7186149147516926931>()) {
                  case -1034266636:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.v.gameMode.attack(this.v.player, var1);
      this.v.player.swing(InteractionHand.MAIN_HAND);
      this.dn = 0;
   }

   public Entity a() {
      if (this.P.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3miygxpgc51u6","MzUv2hFH5Q8iPmRw892ZgbcRBXMEK66XO4FeWdzSO78=",4126086695264641666,150403672988904023,-7882578812768270632,-3308803079445883145>()) {
            case 123804619:
               switch ((int)com.yiyiaddon.m.b.a<"s1e3oy5g6tl5os","BOAFAsXkEAeLRKrTJ7Lff4Nrk3j/P+I7oWPi9+PTbro=",9109720245129916808,-647311118911886610,-7502960226637140949,2219777311084819263>()) {
                  case 1472690885:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Entity var10000 = this.P.get(0);
         switch ((int)com.yiyiaddon.m.b.a<"sugec9sbdvkt1","cUD3CbJiidroWnPDoLfkkoc42qnJpp+5o2FmayQLk8E=",8351431950880921079,5345087330673286430,-6827233962090670427,6105637566079640837>()) {
            case 80428667:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private void b(Entity var1) {
      LocalPlayer var2 = this.v.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m5t9dvy77qd","ZtlRs96U3UymS44ojJxa7xcqg2G625oVG03AgtVb+9Y=",4583347770829644373,4681115482855069115,2902358423316155847,7614731494568696117>()) {
            case 2135356311:
               if (var1 != null) {
                  var2.setYRot(a(var2.getYRot(), (float)com.yiyiaddon.e.f.b.a.a(var1), 15.0F));
                  var2.setXRot(a(var2.getXRot(), (float)com.yiyiaddon.e.f.b.a.b(var1), 15.0F));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s13uwvias5kw4g","bLm7pJecRgFV5IQgAv/dDJb9rhW3EYUGTBC0oR/fSMQ=",-7977638564495530324,-7667491543378813068,587108630668991017,7405661717013260681>()) {
                     case 819865406:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static float a(float var0, float var1, float var2) {
      float var3 = Mth.wrapDegrees(var1 - var0);
      return var0 + Mth.clamp(var3, -var2, var2);
   }

   private int a(Predicate<ItemStack> var1) {
      if (this.v.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kdlmzv1y9ifl","Zq7VEROd9vEs3IxcOxEQQ1wt7n29x9ABcCoQAD3c9PQ=",-3057909593132316792,-6499619049940845164,1349007551045761070,3551136574165137055>()) {
            case -754170199:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3mkp14bf3h17g","FaivYGhs4DINpKiESyvY/jcomvHKJdy5zfXR+mOK1qE=",1220500809600420295,-5925970861224389100,-7715488562532894550,-3374407699267028979>()) {
            case 30524368:
               while (var2 <= 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"shk98qa0m7vy6","hYIpYtAo+EOxcMb7FiI54yzsnBs8XWta1by2UsonHsw=",-8074666780329733013,-1085852266235860586,-1146738752914502814,614476487079081437>()) {
                     case 1445553402:
                        if (var1.test(this.v.player.getInventory().getItem(var2))) {
                           switch ((int)com.yiyiaddon.m.b.a<"s24i385e1et412","f97vXuDtPFZ8UmBtv4zFhHRW8NGbnD2Q0sprttO5UjA=",4798214084912299275,-1223903419037399228,-2048827941590252412,6239618177740743861>()) {
                              case 241935867:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3p4itf0hs9sf0","3s2WNN+Al/P9ewxm3CFsse55hThAZZ9eDTD4v/hP5x4=",-8558864679869673771,-8682138029171296130,7983825425811817999,1622608315040419347>()) {
                           case -1972745658:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return -1;
            default:
               throw null;
         }
      }
   }

   private void f(int var1) {
      if (this.v.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s22456ukxtlexk","BIqdNOIRw+nZjAz8qt4gsZ4pqQ5FCJimd6oLOeGPgAk=",3616595711579883662,1143755808936070942,-1166056967021668389,-1586246349245930695>()) {
            case -1110325484:
               if (var1 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3g12m41xejrpo","GHEz6Ruvs4ExLVLMAGqLGMEokPI6i41x7L09UVZJs7E=",8162597870576928243,301719996962252788,1071783952918794898,8533686890095810336>()) {
                     case -805750171:
                        if (var1 <= 8) {
                           if (this.v.player.getInventory().getSelectedSlot() == var1) {
                              switch ((int)com.yiyiaddon.m.b.a<"s36qfpfjj7jaxj","OAk8cz0937tbBxPO9UEqySI5P+2XoHFxDZ84yv3a2Hg=",-4468227750518151374,-6413019158130222781,-1675584014149325494,-6105773951690866094>()) {
                                 case 1484917304:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           this.v.player.getInventory().setSelectedSlot(var1);
                           if (this.v.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1j8vrb07ellqm","V3N1SlU+AtxWDeEWaqsrq5aOgOrmRgU3XWC59shjmgA=",4926254902550717708,-1574846026485748023,3182332037102219397,-3259369058542145730>()) {
                                 case 859836071:
                                    this.v.getConnection().send(new ServerboundSetCarriedItemPacket(var1));
                                    switch ((int)com.yiyiaddon.m.b.a<"sjcxx26hvaahy","53qW+98S00bgXYb9ygYZrdX54XRh1IDJk3EaNjfkVus=",-4334517430247492948,5879491614439256202,-3058723057599127916,5735315314685940594>()) {
                                       case 461978761:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3v4jmk7vrwpql","5r5aK12v8s76v+1tMbeXvNqTudN94TejgUeiARsdXcI=",1837681772875115852,6846903120557274366,-719486621851253942,-3925992893619024875>()) {
                           case -1914131594:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }
   }

   private GameType a() {
      if (this.v.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hdy9s69t3wik","O/YvprWP1+K8Fmz8Tg24ahArFnH9QhjgrOMS37UEaOM=",5291770667999602120,-5458150399832684818,7920054114438004926,-5938570372796403140>()) {
            case -780534222:
               if (this.v.getConnection() != null) {
                  PlayerInfo var1 = this.v.getConnection().getPlayerInfo(this.v.player.getUUID());
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s38a8ek07y6txd","etfo+FA+CHrRttGa1b2C3upr7JQSmWKpNgSG2YJrNgo=",317810504866615082,-8452939783561015596,-735422900314818909,-1933670492198906082>()) {
                        case -754129698:
                           switch ((int)com.yiyiaddon.m.b.a<"s1aclh81w6z1wc","S7oU9KarNfaySS6yTQTAp7YaUFFsuFOOGz77TkCIxBA=",9128553904617467319,8930504207591696871,-6146564806989722850,-5611983225669501699>()) {
                              case -1022186478:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     GameType var10000 = var1.getGameMode();
                     switch ((int)com.yiyiaddon.m.b.a<"s1pq0daku0yycu","7RBNx6LvYkRDgzOU4h50q+glhCzTVtA+/uFH5j2/9/8=",3978896309489129336,4830387790499368176,1852157251284926958,-6175978830439249125>()) {
                        case 1157797090:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s21p3u91holh2z","RSIiIRC1TGSNkyqXhSUlqh9DCI9bXzQjPOQA+Me/gU8=",-7297235543618726694,-7137153018789171355,-6329810184096525864,982449080751407226>()) {
                     case 2006291443:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private boolean w() {
      try {
         return BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing();
      } catch (Throwable var2) {
         return false;
      }
   }

   private void bq() {
      this.bs();
      this.aP = true;
   }

   private void br() {
      this.aP = false;
   }

   private void bs() {
      if (!this.aQ) {
         try {
            BaritoneAPI.getProvider().getPrimaryBaritone().getPathingControlManager().registerProcess(new com.yiyiaddon.e.f.a.a());
            this.aQ = true;
         } catch (Throwable var2) {
         }
      }
   }

   private final class a implements IBaritoneProcess {
      @Override
      public boolean isActive() {
         return a.this.aP;
      }

      @Override
      public PathingCommand onTick(boolean var1, boolean var2) {
         BaritoneAPI.getProvider().getPrimaryBaritone().getInputOverrideHandler().clearAllKeys();
         return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
      }

      @Override
      public boolean isTemporary() {
         return true;
      }

      @Override
      public void onLostControl() {
      }

      @Override
      public double priority() {
         return 0.0;
      }

      @Override
      public String displayName0() {
         return (String)com.yiyiaddon.m.b.a<"s1bfnzz9jqdi0o","Nwhu85R3AmoCLZRFqQsYevebJor/C13tr+PMQZONS7hwU+M9j3+yk5xjp1AS8g==",-5908111791643744772,3968686547453463790,-2615063658706542387,-4642807328567001053>();
      }
   }

   private static final class b {
      private static final String hE = ClientboundSetTimePacket.class.getName();
      private static final int dp = 20;
      private static final long l = 4000L;
      private final float[] a = new float[20];
      private int dq;
      private long m = -1L;
      private long n;

      void f() {
         Arrays.fill(this.a, 0.0F);
         this.dq = 0;
         this.m = this.n = System.currentTimeMillis();
      }

      boolean u(String var1) {
         return hE.equals(var1);
      }

      void bt() {
         long var1 = System.currentTimeMillis();
         float var3 = (float)(var1 - this.m) / 1000.0F;
         this.a[this.dq] = Mth.clamp(20.0F / var3, 0.0F, 20.0F);
         this.dq = (this.dq + 1) % this.a.length;
         this.m = var1;
      }

      float a() {
         Minecraft var1 = Minecraft.getInstance();
         if (var1.level != null) {
            switch ((int)com.yiyiaddon.m.b.a<"si52hcfotlwb6","hsZQarO6eFY1kBnlD24NxvAqoxcMCBKXH2/EbBhb2ZA=",-5379804444933645860,-3084622719823226159,-1130007831260070326,-5347193905545727641>()) {
               case -1222471332:
                  if (var1.player != null) {
                     if (System.currentTimeMillis() - this.n < 4000L) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2xd9mmw8wj3p7","B8Q00W2u6XR0f4QQGFj+HJj02HinfJpNyNU1N4p3d68=",-2290609438741507404,2508657487734587365,-3757438812960863160,-1408584315432274445>()) {
                           case -643302522:
                              return 20.0F;
                           default:
                              throw null;
                        }
                     } else {
                        int var2 = 0;
                        float var3 = 0.0F;
                        float[] var4 = this.a;
                        int var5 = var4.length;
                        int var6 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s188oderc87xyn","2o/vxhD+BQwvocnqOKv+YygqxwclmGOyw6k3f0XVzZc=",-4731898265411140766,-3151698680164380109,6117519158114123745,5938430829526484236>()) {
                           case 1272942349:
                              while (var6 < var5) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3esy6vwn9fp9c","XGQn1CkReE/acntkktpXN+k7UEdZExu9onCyssHCPOo=",2990430333555862536,-5408166129629003647,649451305730989056,-2777308381934973918>()) {
                                    case 2002743426:
                                       float var7 = var4[var6];
                                       if (var7 > 0.0F) {
                                          label49:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1cpx3scxfeyv6","fG9kBFYOPeqpbyxJokEpMPGc1+6GXe35LCBZSxkp0ZQ=",-5184894972191802365,4205731309711383368,-20209281305501904,3316232553843929507>()) {
                                             case 1701475834:
                                                var3 += var7;
                                                var2++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s348ku9kvjdmbn","VEGHyMJbTaDEIuy3LadD3dyaC8r41KHv12YlxZKsxDw=",2061957529552287689,2275148882928670925,-8189734101255579384,2063007140411476084>()) {
                                                   case -1310955232:
                                                      break label49;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var6++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s32vxuda9yn8zb","ld1CDMe77uCjy0Fe6PmvvFGQ1fHeftG0mMNJN1MJgmA=",-1966798669645568283,8554794104205061102,-4034715749738194707,-4077567243563057746>()) {
                                          case -948549672:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var2 == 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3bk2zqznk28e2","bPTF1EsekeflqaS3YoQLhwhUrBAh7CufYYBkQ1DMZJI=",4861341357374790089,-8059462186649168809,4554561865618903562,2886993531321903928>()) {
                                    case -47709312:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3jsxyhzc1u4k5","De6B4gXgdJ6qeRXZjG7OcaygdWHnb+d7d0cbMtx57l0=",-3808496729868089902,-3943718543119202946,-3272580916869600173,1885060623349095375>()) {
                                          case 1830660039:
                                             return 0.0F;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 float var10000 = var3 / var2;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1dbaimb74ywie","5gR1kruo72YgyT85UWFd8/7JQyvAGvkaS+erbGOaCno=",-4781522806667950672,-5742159469124998322,7514262024562324616,-4455572495895669821>()) {
                                    case -69390231:
                                       return var10000;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sim4hr2tw7isr","1xwETQoWOJJE0hxCWA1qJqRx/qd+pzNhxFPNNig2Gbk=",-5354790838592732755,-6657712486907230237,6215476052995483600,-7412405585760538258>()) {
                        case 1515908750:
                           return 0.0F;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return 0.0F;
         }
      }

      float e() {
         long var1 = System.currentTimeMillis();
         if (var1 - this.n < 4000L) {
            switch ((int)com.yiyiaddon.m.b.a<"s1e0mcwqux78i6","9OqLhUGy79popiQJJnBrHja7gl0ZcHMxZFjP0DfQg90=",-3085082901251674971,5810712599288809852,-8469601522738641462,-4841508823417590481>()) {
               case -1789887222:
                  return 0.0F;
               default:
                  throw null;
            }
         } else {
            return (float)(var1 - this.m) / 1000.0F;
         }
      }
   }
}
