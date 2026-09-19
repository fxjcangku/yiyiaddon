package com.yiyiaddon.e.q.e;

import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class a {
   private final Minecraft ap;
   private final com.yiyiaddon.k.a.a g;
   private Consumer<String> a;
   private com.yiyiaddon.e.q.e.a.a a = com.yiyiaddon.e.q.e.a.a.IDLE;
   private int qQ = 64;
   private int qR = 0;
   private int qJ = 0;
   private int qS = 0;
   private static final int qT = 2;
   private static final int qU = 100;
   private static final int qV = 8;

   public a() {
      this.ap = Minecraft.getInstance();
      this.g = new com.yiyiaddon.k.a.a();
   }

   public void a(Consumer<String> var1) {
      this.a = var1;
   }

   public void J(int var1) {
      this.qQ = Math.max(1, var1);
      this.qR = 0;
      this.qJ = 0;
      this.qS = 0;
      this.g.f();
      this.a = com.yiyiaddon.e.q.e.a.a.RUNNING;
      this.aW(var1 + "");
   }

   public void ae() {
      if (this.a != com.yiyiaddon.e.q.e.a.a.RUNNING) {
         switch ((int)com.yiyiaddon.m.b.a<"sl0ai7a5wh0zl","jEkg5z8jtPuKpsP9E0D/FBNl3DvY6JqJZw0nzM5cVz8=",859392860964928013,-1478477539893201893,-6720341185975955721,2466313394874402281>()) {
            case -1140324421:
               return;
            default:
               throw null;
         }
      } else {
         LocalPlayer var1 = this.ap.player;
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sifc11q57q07w","x3/qBJ+dQRD35lA8StbkiLNovJbpmR5H0/uPtDsm5Og=",-4348370884494242625,-1982571457597868432,-6028111948289697837,-7950202763623993499>()) {
               case -532816911:
                  this.a = com.yiyiaddon.e.q.e.a.a.ERROR;
                  return;
               default:
                  throw null;
            }
         } else {
            this.g.ae();
            if (!this.g.fr()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1fhh2udhs1eut","GWQ6BynI5Xmk49kR/OBTGKMini2coxp+sywLEru+t9g=",-751383549528026515,-1996248854305834429,-1258516974661768525,-8086471947676934869>()) {
                  case 1668064006:
                     if (++this.qS > 100) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1n4x410keue6m","SqH/KoDFGSEkWzsUgdvG7MsJYG5cIsAu4OGf+s+o4l0=",-2080844819569207786,1748434910580248612,737203006189220055,-706268131638620518>()) {
                           case 1244948633:
                              this.aW(
                                 (String)com.yiyiaddon.m.b.a<"s2lklgi0dpyeuo","67utpfZSbqlFlZ83fo/vcsY0L7IrZxmCOFOM02plSeUBaDDvA/ltJc9uq9o=",-9192993681224929674,-2923139385548392443,7740776917510644486,-6784727119396077340>()
                              );
                              this.a = com.yiyiaddon.e.q.e.a.a.ERROR;
                              switch ((int)com.yiyiaddon.m.b.a<"sjpdtaf48kpch","RBan0y+54L24/kPaO5AXpz30iQErn10E464Rj+bCr7w=",-7490017150952749266,1017048257820064008,-3591134076163741541,-2071920645940515583>()) {
                                 case -328346048:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return;
                  default:
                     throw null;
               }
            } else if (this.a(var1) >= this.qQ) {
               switch ((int)com.yiyiaddon.m.b.a<"s3b0kwtvxb236y","/SBuuxqZma8fyIODqX+6i+6k/vLe6zJc6Kr2FZgxB38=",-5801432518516032045,8332875197792133226,-4436744839148430158,-586024216608874990>()) {
                  case -171084624:
                     this.aW(this.a(var1) + "");
                     this.a = com.yiyiaddon.e.q.e.a.a.COMPLETED;
                     return;
                  default:
                     throw null;
               }
            } else if (this.qR > 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s2pu3uidx2nvnd","zouf0wtDkQ/Si8uU/QIkhaTjx95eygwldL/QZmw5wwM=",-6724085178006473611,9136200968917642470,-6100393256088178317,-5010332939997946790>()) {
                  case -1396189797:
                     this.qR--;
                     return;
                  default:
                     throw null;
               }
            } else {
               this.qR = 2;
               if (var1.getInventory().getFreeSlot() == -1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nedv38aivcmi","q3xoMTSmGZzfAalEXVsnXtaWw0+L/mIpTfRmFyqaSzM=",-7573784161437733245,8381981475998759369,1591573359543455975,-4547651834776018329>()) {
                     case -487150517:
                        this.aW(
                           (String)com.yiyiaddon.m.b.a<"svocowsyigk5b","RbwsWkMi2GnPGWfloRxZXSPNWx1BlcVIezqHs4EpA7OHMAbCfTBqqA/jhTTsiYCT7quCNK6A",-6088874706314131826,-5573580542875575765,4121920830516443717,5558236914276105956>()
                        );
                        this.a = com.yiyiaddon.e.q.e.a.a.COMPLETED;
                        return;
                     default:
                        throw null;
                  }
               } else if (this.g.f(Items.EMERALD)) {
                  switch ((int)com.yiyiaddon.m.b.a<"ssdn9r1cfoens","6SRZAHDqaQXVL6yzGK8eN0wHnjh+i0olUdwOyLyXgT8=",6782315903643454779,5459893869492810194,-1871806280939755743,1767507931603599695>()) {
                     case 944221559:
                        this.qJ = 0;
                        return;
                     default:
                        throw null;
                  }
               } else if (++this.qJ >= 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3b9bk6a0jv56","0svO4661UqBT/YB85nsEdKESTMUDEHS75NEndkzNFNw=",3954201205712861883,-6326992387247198890,-161459484670721654,3053005331989828074>()) {
                     case 885605994:
                        if (this.dd() == 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2jay6c5fjlh2b","XPHRb/pIkA5BiQ5op89RhDO7aGxjDNvxSxbQVt7pVXY=",217243298004088235,-7630279873421660129,-7394893627627792565,-9150774898802982165>()) {
                              case 1643670573:
                                 this.aW(
                                    (String)com.yiyiaddon.m.b.a<"s37712wqm8hep1","UE8mYobH22ZQgkgh8DT9dHCsgqP7Y+dAMtYxYbN1lDOlpQx0Cs3FDUDlGezh3vSLAtf3jaj8YNtqeg==",-5884123546252325638,-3825730067434661139,-2035659265521985763,2651617871338680495>()
                                 );
                                 this.a = com.yiyiaddon.e.q.e.a.a.COMPLETED;
                                 switch ((int)com.yiyiaddon.m.b.a<"s25luljmfsnngl","WEdeCFXMfucsIVJs4esQkJeR1J+rBBURq6vWwo5hkJM=",2506521051447223571,4964921190524143512,-2444643865151332701,-587423883584202856>()) {
                                    case 1575314163:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           this.aW(
                              (String)com.yiyiaddon.m.b.a<"s3fp4onyw34ovy","g9udtLY4yH+3QHua1bE59I9KjTkwjwrfHjA0Otl8mb+qhENkyRdD+23dezo=",4946139542360459899,4741911525681864755,1371005574882616917,9189237677589092303>()
                           );
                           this.a = com.yiyiaddon.e.q.e.a.a.ERROR;
                           switch ((int)com.yiyiaddon.m.b.a<"s308pqpd09jdxj","1pfbfG7XI1CgnY4/KLNR4MxXYSACK34rMqyXOe/9WvU=",4452603279482808721,-863950916263358847,6965745097651871153,-7769364259023743031>()) {
                              case -455729916:
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
         }
      }
   }

   private int dd() {
      AbstractContainerMenu var1 = com.yiyiaddon.i.a.a.b();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2js4uu382eh4z","tX5mJ3KMGThxALs17kZJqoyW7ntqJvLCJp6QiF4IzQk=",-8133790612121383241,2546405135684013482,7992363462848586277,576822496480643229>()) {
            case 54360651:
               return 0;
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.i.a.a.a(var1, Items.EMERALD);
      }
   }

   private int a(LocalPlayer var1) {
      int var2 = 0;
      Inventory var3 = var1.getInventory();
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3pyktozg4gsex","/6yE2pHW9YThKOzsP3RY2CHzjl8gKuJK1sgohDXEYug=",7089651543430011645,413902731960410605,3614325674567787391,-6515669346468798816>()) {
         case -1954060133:
            while (var4 < var3.getContainerSize()) {
               switch ((int)com.yiyiaddon.m.b.a<"s36gxm8odqmzdj","x0UD8OkSnQDjSZB5uSpzUVd7Hz6cvxIqhl+9y/z0XVE=",-2612262846974569406,-837329921612485139,-1807520408969141704,3254190361712214374>()) {
                  case 1108647472:
                     ItemStack var5 = var3.getItem(var4);
                     if (!var5.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2zvhhxehdu219","Vc8tQyHFqaQ5U2m5QFdzIC/pQPXousd3oxeikGcg+10=",-5279712548422731866,2250633103123834015,-5053005322124914837,-7086831870410581318>()) {
                           case 1513589576:
                              if (var5.getItem() == Items.EMERALD) {
                                 label43:
                                 switch ((int)com.yiyiaddon.m.b.a<"s8rdcrjtj94rq","CivLEWUmLbOqUmVj+n9C2YQmOPQ+bwVzgkKjzYrtezA=",-4762187304688252325,-5637573040708570020,4963803555313494379,412659170979910422>()) {
                                    case 1678421608:
                                       var2 += var5.getCount();
                                       switch ((int)com.yiyiaddon.m.b.a<"sgvkw5bi7jtki","JqKAAnuYdgc8hqDHMCAIQlgQ6QWaabJdtTTrR/DGCA0=",1148235016550680417,-3967452745593019173,4903425436582279258,4727399246701304902>()) {
                                          case 1340680896:
                                             break label43;
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

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"suj0fr3f45pq9","F43IXxW9tO5M1srBmGMBt0Pt5enTk1HQsAQk2GFx/Vo=",-2729477996428019015,-7302455283909720483,4144013307415772308,-6288367656296398458>()) {
                        case -1171092706:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            ItemStack var6 = var1.getOffhandItem();
            if (!var6.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3c75fuenb7vlt","t9EamMN6MdqbvJ7QipcXwVeB0Yqbw32DUXAtb7mDVx4=",8355698928281649528,-3439258849976487885,3374457753252034815,-6788004622318064701>()) {
                  case 1048171730:
                     if (var6.getItem() == Items.EMERALD) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1b8m4rpty85es","JWHlXsXhIcWReauvlHfD7U9nUM/3Clnfg+WHL/wawLI=",7719672153557068079,5783703282962065099,-8235328837954094975,-305470651651429341>()) {
                           case -980550479:
                              var2 += var6.getCount();
                              switch ((int)com.yiyiaddon.m.b.a<"s20nf6k6uslrm4","mptOjmSQMePin9Y7bQs4HY8/Qi4nr0TgtNZZ/Q0Kd88=",5201742364173212842,-4238924649664488098,2281376825425085480,-6014451614330067499>()) {
                                 case -1703433950:
                                    return var2;
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

            return var2;
         default:
            throw null;
      }
   }

   public void f() {
      this.a = com.yiyiaddon.e.q.e.a.a.IDLE;
      this.qR = 0;
      this.qJ = 0;
      this.qS = 0;
      this.g.f();
   }

   public com.yiyiaddon.e.q.e.a.a a() {
      return this.a;
   }

   public boolean ab() {
      if (this.a == com.yiyiaddon.e.q.e.a.a.RUNNING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jpx5w8ou0c7c","albHJ6cCDzn4hebJOfEBRkaoCMyHJCZZaG3sWpJAwgg=",7101310974681555010,914611143456105456,-4731375572926386559,2759645573638107284>()) {
            case 1192975105:
               switch ((int)com.yiyiaddon.m.b.a<"s1mvzmgixmzro9","reKckUYfL+PkcIb8SEAwu8YYkVj7KsFqGvWow65blZ4=",-4906793842742711579,-7031172845991814308,-5467385848369520957,-8461586407875757343>()) {
                  case -2031553424:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s38y4wnqon23tn","um4djsw7j36tEe/70XvsWk2tt3mI1nwsMQJx77IElmk=",8584839667237108318,8978205474135787599,-6835584067890909732,-5725853695935864475>()) {
            case -19702868:
               return false;
            default:
               throw null;
         }
      }
   }

   private void aW(String var1) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17oiu9vachn48","3R9MzjJ+w0ibdju3WwjuNtXe9QSVDytyqhFESneCE7s=",3523306954520905770,-4756337978615372231,4675655915555166705,-276598271424102416>()) {
            case 1351293237:
               this.a.accept(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s3jrdh458cslaa","jBGyvh8+qDoF8IZ5VQRQZIgIqUaIK5vjf29rA5Kv0NE=",9094229199331455139,8834802400575487147,8377129402518770714,-5438855510378371041>()) {
                  case 385291829:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public enum a {
      IDLE,
      RUNNING,
      COMPLETED,
      ERROR;
   }
}
