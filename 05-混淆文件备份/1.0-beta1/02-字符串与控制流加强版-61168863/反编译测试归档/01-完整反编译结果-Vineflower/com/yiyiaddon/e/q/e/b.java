package com.yiyiaddon.e.q.e;

import com.yiyiaddon.e.q.f.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class b {
   private final Minecraft aq;
   private final com.yiyiaddon.k.a.a h;
   private Consumer<String> a;
   private b.a a = b.a.IDLE;
   private List<e> cp = new ArrayList<>();
   private int qR = 0;
   private int qJ = 0;
   private int qS = 0;
   private static final int qW = 3;
   private static final int qX = 100;
   private static final int qY = 6;

   public b() {
      this.aq = Minecraft.getInstance();
      this.h = new com.yiyiaddon.k.a.a();
   }

   public void a(Consumer<String> var1) {
      this.a = var1;
   }

   public void q(List<e> var1) {
      this.cp = new ArrayList<>(var1);
      this.qR = 0;
      this.qJ = 0;
      this.qS = 0;
      this.h.f();
      this.a = b.a.RUNNING;
      this.aW(
         (String)com.yiyiaddon.m.b.a<"st582g9nt45cr","nxdQkMCxd2nyMOd4fCtwJgFnVnxHOB0aOl5XbkX5MIYDvA7oC6UBTg==",-6422179216587724431,5718877452752268811,7668073572376380293,-6661105608910171359>()
      );
   }

   public void ae() {
      if (this.a != b.a.RUNNING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vu7nzt0z96a5","dvvQQm9t6y170I9E7KDJg0InRIMxRCUnBQd/PZ9aj9Y=",6420059654299258224,-3847053040139651228,-408447263662827132,284076333080366008>()) {
            case 1583689596:
               return;
            default:
               throw null;
         }
      } else {
         LocalPlayer var1 = this.aq.player;
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2o1b20nzi2ky6","fjYsF/GuSFk5sCCD4281lUYgb56TEJmrAZcMCqH8U60=",-8587032812501327318,-6272550138383759199,-6059876057952868090,4665731940591177055>()) {
               case -1396371525:
                  this.a = b.a.ERROR;
                  return;
               default:
                  throw null;
            }
         } else {
            this.h.ae();
            if (!this.h.fr()) {
               switch ((int)com.yiyiaddon.m.b.a<"s6clv6kd56lus","TcM+3TYuZKJychLLoH20ohlOuI1zUNjzlTVGHJYmpQA=",4206780487416422883,6906392811604552798,492692615684654754,-6707842908587323413>()) {
                  case -1175393629:
                     this.qS++;
                     if (this.qS > 100) {
                        switch ((int)com.yiyiaddon.m.b.a<"s33hze1tsskhns","VJjpUjOccHl/ucX7V3PfchOsoLHIv6AXNMTOcz+IwKY=",6644707812055844498,-537803925326519501,-565525033105825751,5101815025609389699>()) {
                           case 2047967034:
                              this.aW(
                                 (String)com.yiyiaddon.m.b.a<"s3uxwbpoy5cl5s","7Ag+v43NTt54cR/QZAi7UzAt46ndkKU4PaGm3iEm0KAnd8er+RV82Lb7qyk=",-6865565974504424940,-7748755263576455346,9185952140288876791,-7718512197682163435>()
                              );
                              this.a = b.a.ERROR;
                              switch ((int)com.yiyiaddon.m.b.a<"surg2xnj09pa4","k8D9QfXyh9btrKiwfN6mKUMzxaH8gLwNwvTe1TXdU5Q=",1223936548436558293,2170707734491967215,-7798598955493050301,-8806532498212512383>()) {
                                 case -1976684471:
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
            } else if (this.qR > 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s2pm2ltgzdu5w7","XKoQc0Z3eeCXrRpoMZMKCpPKGB+nqINVJvpmqhfS4MY=",-2885854506884799474,-3792945254138948978,3003999069079823682,-2269342299634702177>()) {
                  case 955911562:
                     this.qR--;
                     return;
                  default:
                     throw null;
               }
            } else {
               this.qR = 3;
               com.yiyiaddon.g.b.a var2 = this.h.a(this::E);
               if (var2 == com.yiyiaddon.g.b.a.MOVED) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2di0bpd85rv03","LUhtiB41b36jsOecAPvkmbeDhyIqYZ23rsDapfh+/P8=",7905613367972935043,-8510432604227865252,-1084309918572483358,-2897265559989014088>()) {
                     case 1028313666:
                        this.qJ = 0;
                        return;
                     default:
                        throw null;
                  }
               } else if (var2 == com.yiyiaddon.g.b.a.NOT_READY) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2t9mlo2vjbdfm","go5KcZAexjufuQ9bxz/7/fTYBvfjmqtz/jd476KFD14=",-2397635812494449685,-3638793234928386463,-7047322242891133694,-1975617256989219200>()) {
                     case 1840890003:
                        return;
                     default:
                        throw null;
                  }
               } else if (var2 == com.yiyiaddon.g.b.a.CHEST_FULL) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1a6t8fetto9hh","wig3qA8VydHBCQ3w/s3pKTlBbbX4JoUa88fYNIDbk9A=",-2321159354920229556,-2033048003676925199,-2581351322118731479,-2023934994167185094>()) {
                     case -1627286258:
                        this.a = b.a.FULL;
                        return;
                     default:
                        throw null;
                  }
               } else if (++this.qJ >= 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nk6zwn5vouxg","Id/3pllEto/NIxcfdk7m/wgvgZp6yaJCp7JKEKLV+aM=",-5600519877771495643,6539189559909383356,-6660252768135675659,-8224773964585926437>()) {
                     case 168930608:
                        this.aW(
                           (String)com.yiyiaddon.m.b.a<"s3vtxq203caa8a","gPQwNd9l3jDTqoxl7pmCdgN0i7IEKv4pv8aQzymGuDvl995JXaAKsg==",-5857406277441175264,8238142469917362297,9211639798543720063,-67116690724954947>()
                        );
                        this.a = b.a.COMPLETED;
                        switch ((int)com.yiyiaddon.m.b.a<"s3g24829gfi8vh","slFkLSbK5Gg1m7OIbpXJEEazmuau0xbYtaU8Nym7Rxw=",-3167740907922013315,8770281051386950895,450223178867519454,-6734141246024696296>()) {
                           case -875992179:
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
   }

   public boolean d(List<e> var1) {
      LocalPlayer var2 = this.aq.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1z5mgj98wr68i","NjY8GBfn5oWWk0jFe+fBr4Rv7Mo6xyymczRClUGvxXM=",450139416841485851,7145117871764625,-6523698362797236450,249072337543477314>()) {
            case -466120768:
               if (!var1.isEmpty()) {
                  Inventory var3 = var2.getInventory();
                  ArrayList var4 = new ArrayList(var1);
                  int var5 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3kgi1pf2ofyis","zF6OXK9kNotKxEP7ZIPJJyspR1cMMlHvJRAks7WBSn0=",-3105366745011170735,5488335455519171428,-3423204990503590293,7557996469497572126>()) {
                     case -1897738926:
                        while (var5 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s177l75eusnq4j","tIiOah1of4TQD7Ka0PL7FzIZrRK13AoMPo6oCPyfrxM=",-7371138604141375734,-7860880636842291319,-5936874374086944544,-7509600249643924928>()) {
                              case 308737246:
                                 ItemStack var6 = var3.getItem(var5);
                                 if (!var6.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s36anu40vkgejo","3Ky+vKdOVvnxAcZ7nGztQ7ne/N6/eef0ogQmLsYam5w=",-8207593847001325072,-4411345782418862024,2470559029134176885,-6005832338889377266>()) {
                                       case -748899266:
                                          if (g(var6, var4)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s16dh6vr9as50g","O6rEwxpQS20D86g/pJvHziNtJcNEHk37xTgc443eKNM=",8361179898337708630,-7819085766397127589,1657107315946328805,7530442875115781680>()) {
                                                case -24306500:
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

                                 var5++;
                                 switch ((int)com.yiyiaddon.m.b.a<"svwrmzelqts33","HmCkHwyYqHwGKbJLeheUoXtRDA1NlZUerB+uMhpVfaM=",4967455209821484056,-8889903748669006338,-5507678130091284103,3865327871478061522>()) {
                                    case -1388546323:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2sqz0wjwcsrpp","ondhamzpSBpq40rE6+qLr2T3lgQ8xA5QkquHIwVjle4=",-4144535831175687446,-2972640454458953704,2003042848153145797,198955575323076490>()) {
                     case 1752372616:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private boolean E(ItemStack var1) {
      return g(var1, this.cp);
   }

   private static boolean g(ItemStack var0, List<e> var1) {
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s859gyjcpb3bb","rQfIuAdeGALwnKUAR9nDmQfMMuL9lkPlDcrjGCGs9BY=",-3795044052355771473,5301986257076109251,-4698488747691675463,1596721121847016132>()) {
            case -1144374904:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var2 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s4og1cwq7lfj1","9nLUEwHoWwI7a5JsfGWGOCOFL/IXZMb2A+T11KFqLG4=",2317625904366326464,-9034938721941019460,-2109468299717653344,-684022657017675159>()) {
            case 1970877532:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3je2nb9xsaght","doxiSFGBRuPsjp8XxZEsTzWksd5+pHUdLgybVl+ir7U=",-3344244846648690825,-3170633099283372224,5612737688816142178,5678810259550336207>()) {
                     case -382289311:
                        e var3 = (e)var2.next();
                        if (var3.k() != var0.getItem()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2as78hgn39oi7","4SOrIWDM4RhTsee/CIn27x6JMvdHyQepXW1kSz/eqJI=",-1323800821808862792,3993131977668005301,2125707714129213129,-6167470827020061550>()) {
                              case -1082358873:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2sc1g6mpn571h","oThgYOXrlEnjX9l3WIZ0XNRsxQZf9kjgBYKMuwM+WEE=",-35273421329283408,3392018588532964324,-3576741784786911782,5106209230899159799>()) {
                                    case 448215599:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (!var3.eO()) {
                              return true;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s297szo64i9ik1","EkIqZfOFFa6WtrseirFyiSWkuykHykde39nqLdvTKu0=",7773409818835814700,5184497369163707593,-4683968757853323914,-8434714981121737225>()) {
                              case 1952169336:
                                 if (com.yiyiaddon.e.q.j.a.a(var0, var3)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3hlt7prx33umg","HFHvo8ukhf79HygzMYzUd5L+Qk1CinHu8lNH9XfJcmU=",5178791651371865646,-619967024220497327,8950910511245006701,-8235958136290047705>()) {
                                       case 1254312662:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1k34r4exhr1uq","BFrVZipZvBR4Ur8KBq00Yl/0SOM5dGx911u+Y2CTsOw=",6674674398140962099,-5963644033583494615,4594924898182105170,8446814560749591044>()) {
                                    case 1359711905:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
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
   }

   public void f() {
      this.a = b.a.IDLE;
      this.qR = 0;
      this.qJ = 0;
      this.qS = 0;
      this.cp.clear();
      this.h.f();
   }

   public b.a a() {
      return this.a;
   }

   public boolean ab() {
      if (this.a == b.a.RUNNING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ek3it7u6oiwo","shyU9ch0yD15++mkie7sLZdhpD00oP+RUnyJe6ckjV8=",-2113079475034034866,-7649682649433542578,-5877545266472883345,-8723716805867091032>()) {
            case -2008702745:
               switch ((int)com.yiyiaddon.m.b.a<"s36kx2sqrao7qi","ep2EefPDt0Sm3OzWW79OO+DEhqXdkM7cG7RcUlkYLF0=",-9052463533732439282,2194344464129822970,-2736962729491597011,-8779893303141214343>()) {
                  case 1598301327:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"sy1cz5fxpmtj5","IqfcbDCbK40O8OFfb8yWQume1tj+g+/zNzH227RNTrU=",1362751554128782780,5874896725090964511,-1134355708264516548,1392564036799384112>()) {
            case -1573925223:
               return false;
            default:
               throw null;
         }
      }
   }

   private void aW(String var1) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3b1bonhl1zue0","CvcUs9fBoCpvVh5mW126iWtRzi5saYn+YFuN4kXx/wM=",-1590164469804687277,-954139118111179979,2604852262559708477,-3000742100883787163>()) {
            case 1570554783:
               this.a.accept(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s34lc4o0z3kyte","EjhEmbvLaXdFwv9AkixiLXqCEIq6lJmCtmwCbRWhxC8=",-1757065269049074263,3019744452062385606,-2877969419939743107,-4445893321026401544>()) {
                  case 291809459:
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
      ERROR,
      FULL;
   }
}
