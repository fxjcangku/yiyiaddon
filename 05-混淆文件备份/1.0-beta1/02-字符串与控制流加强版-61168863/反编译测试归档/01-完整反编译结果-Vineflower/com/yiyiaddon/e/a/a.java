package com.yiyiaddon.e.a;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.a.c;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public final class a extends com.yiyiaddon.d.b.a {
   private static final String as = (String)b.a<"s2ygsokzilaafg","y7QXEHEet7VvhAm1LIzvpTIN1yT5jGUkuflpDPME",6457567379829077574,9036026944882427847,1667200733372744320,-6455232373507306997>();
   public static final String at = "admindetector";
   public static final String au = "管理员检测";
   private static final String av = (String)b.a<"s1ett9d6x0iywo","f7634Z9LuizMClWEi6pE0QRj1RNdM3RJRXgsl+3T+vy5Egqv1KK9gm1Jg9I77OvS+YmLfH23mLGNykv7",-88366810247036378,-2115143906107080663,2850306105273707606,2694368491780998717>();
   private static final String aw = (String)b.a<"spzxf3q22xy7u","KN7ahRkgU59wrp7aDPW0YGr8tr+n/jUMstobVsbGB0PNN+Tk8ZiUBNo7ufckivj8tLjEIV7WCHayZ+Ox",7561937201373631495,2512552707295955390,77533723998185055,5721173793861146373>();
   private static final String ax = (String)b.a<"s18325ok42z6nn","rmScs7rdmgVQpHpFDXmkY8tqJqhBqEbUknQN66zUfjed+8H9JdlvXlQ3jkz07vqb5OeKpQ4cOi/BCoaarN1LPxdn",-3876957596324111426,7041818298413841197,-8120434588440207661,3332846507515242188>();
   public static final String ay = "检测到危险玩家接近";
   public static final String az = "手动触发自动断线";
   private static final float c = 0.8F;
   private static final float d = 1.0F;
   private final Minecraft a = Minecraft.getInstance();
   private final com.yiyiaddon.e.a.a.a a = new com.yiyiaddon.e.a.a.a();
   private final List<Entity> c = new ArrayList<>();
   private final Map<UUID, String> i = new HashMap<>();
   private final Set<String> e = new HashSet<>();
   private boolean n;
   private final com.yiyiaddon.e.a.b.a a = new com.yiyiaddon.e.a.b.a(this);

   public a() {
      super(
         (String)b.a<"s2eohk2a2pp01x","odnyzU9GwJRYzvycoXjA3dqcVN6lpn5RcXOKz+RMGy4hQJ5STCXS9drRYhcQcxm332iku9jW",-7075005784102095870,8014654305447063532,6510012592675423673,-23444038320095089>(),
         (String)b.a<"s1lvjcdcmu1197","kNh0qd4E0evjqP7A3iGsKB1qFfrmWx6WlOqqySr3AZD025DU91o=",1639351974154479831,358855723496457584,2997462213565017324,7396228753536337619>(),
         (String)b.a<"s3ssti1n238nct","HuyfEh3aWgupKhzX8LqNqlL35Z8vRnFx11eguYStafcONqLlt4ek9E7H",-6934033104075311941,8356405515761821029,6177046999928011977,-505823192231742567>(),
         (String)b.a<"s3i07se2esoz86","CjueqwfypbVG9bp/GCX82470BU7KgoB6mt/J0E/86DmCnuOSa+EFpQVqzO/F0BV1e6PhkU37hQc7F7Qei8VSzjs1IAFrL/VFgvxJhSR6oIcAK9XdZcGdXI/xC+SuuwQLX3oeHyj9jL2JqqkD7ok+NQ==",-1817222209490237499,2649390277391537715,-2401128123114560857,-9142407170562743389>()
      );
   }

   public com.yiyiaddon.e.a.a.a a() {
      return this.a;
   }

   public List<Entity> i() {
      return Collections.unmodifiableList(this.c);
   }

   public boolean o() {
      return this.n;
   }

   @Override
   public int i() {
      return 10;
   }

   @Override
   public String w() {
      return (String)b.a<"s2ygsokzilaafg","y7QXEHEet7VvhAm1LIzvpTIN1yT5jGUkuflpDPME",6457567379829077574,9036026944882427847,1667200733372744320,-6455232373507306997>();
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
      return new com.yiyiaddon.e.a.d.b(this);
   }

   @Override
   protected void m() {
      this.n = false;
      this.i.clear();
      this.e.clear();
      this.c.clear();
      l.a(
         (String)b.a<"s2eohk2a2pp01x","odnyzU9GwJRYzvycoXjA3dqcVN6lpn5RcXOKz+RMGy4hQJ5STCXS9drRYhcQcxm332iku9jW",-7075005784102095870,8014654305447063532,6510012592675423673,-23444038320095089>(),
         this.a::render
      );
   }

   @Override
   protected void n() {
      this.n = false;
      this.i.clear();
      this.e.clear();
      this.c.clear();
      l.l(
         (String)b.a<"s2eohk2a2pp01x","odnyzU9GwJRYzvycoXjA3dqcVN6lpn5RcXOKz+RMGy4hQJ5STCXS9drRYhcQcxm332iku9jW",-7075005784102095870,8014654305447063532,6510012592675423673,-23444038320095089>()
      );
   }

   @Override
   public Set<c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.DISCONNECT);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)b.a<"s194kywdz8vbrh","Ip1zBa6F74I94cva2I6lalfRAP/7lTvbvV6Gd+0L4DU=",2115535614507080239,2408742049989066815,-284638996312741259,3287575841007800904>()) {
            case -475020293:
               return;
            default:
               throw null;
         }
      } else if (var1.a() != com.yiyiaddon.d.a.c.DISCONNECT) {
         switch ((int)b.a<"s1xmybcirzny9r","ODS4UFgj0rFM6disWcBzQlGioCNSpAE8Qc80UegOHps=",-2736411318455434979,8411524968709030831,7777149348224837459,4780458103006475109>()) {
            case -281340371:
               return;
            default:
               throw null;
         }
      } else {
         this.n = false;
         this.i.clear();
         this.e.clear();
         this.c.clear();
         if (this.g()) {
            switch ((int)b.a<"s3de4w48kj1hfw","aL3tTMJheKVxNCs+irqDIKyENncUheqy4VoKYnbiqzA=",-3561215821631217027,-4931530482616723867,4989032189493149823,4961043263992695789>()) {
               case 182078862:
                  com.yiyiaddon.d.b.e.a(
                     (String)b.a<"s2eohk2a2pp01x","odnyzU9GwJRYzvycoXjA3dqcVN6lpn5RcXOKz+RMGy4hQJ5STCXS9drRYhcQcxm332iku9jW",-7075005784102095870,8014654305447063532,6510012592675423673,-23444038320095089>(),
                     false
                  );
                  switch ((int)b.a<"s1ylbx9yz22gl5","z/OPq7C2ZTVjGlvmHFPKvyNrIHJ5OvVVL2rxtaWqgGA=",1461589758035040035,8980606309923135494,-1156657921069111498,872942856784549439>()) {
                     case -1326941700:
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

   @Override
   public void b(Minecraft var1) {
      if (this.a.player != null) {
         switch ((int)b.a<"s3umxjn3u92fee","zooJ9zA3QiUy0rjRgdA4vJtL1X3dWFbGW0A9DCxQ8Q0=",-616791166320575980,-4343382205320135998,5989955718104736150,8887385972456662914>()) {
            case -1934306222:
               if (this.a.level != null) {
                  int var2 = this.a.B;
                  List var3 = this.a.d;
                  List var4 = this.a.e;
                  this.y();
                  HashSet var5 = new HashSet();
                  this.c.clear();
                  Iterator var6 = this.a.level.players().iterator();
                  switch ((int)b.a<"s1tuhrtvnvbckz","oKvDA3IBItGbJAk7Nhps6qiXi2jUgAGzUqPGogelA/o=",-2509919067267105440,4858735740818585893,-17055958818976342,772604067446423734>()) {
                     case 1341309147:
                        while (var6.hasNext()) {
                           switch ((int)b.a<"s2x4hiic8vt493","dJEPzHg8MANgdqvcm4PuhlLSxZUUKV9L8VPydrX8A+w=",-243018738262694731,-6470853821895190066,4999655025210452778,-1315059485927621201>()) {
                              case 986098781:
                                 Player var7 = (Player)var6.next();
                                 if (var7 == this.a.player) {
                                    switch ((int)b.a<"s34kgz2ehbee79","652n7BTrlmcdlSn1JkbmVw03BpRZJSWVdcddx9/jk94=",3767211582804367932,-627354732484401446,-6125155772295189661,-5165999757800162515>()) {
                                       case -71969566:
                                          switch ((int)b.a<"s3emf85t3rk6hx","lBoMsMESFUwms3yp07rBW1Y+OjV3s03XNoCGwK9lG9g=",-7480488921243782047,-6872029409699767693,-7058465898352827408,7142092453014356160>()) {
                                             case 1637612761:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var8 = var7.getName().getString();
                                    if (com.yiyiaddon.e.a.a.a.a(var3, var8)) {
                                       switch ((int)b.a<"s1zsby2tcixce1","lzAbVibJin5WZSfqUyOH+MVXW6xlalZmvszpiH4Jv1c=",-6675762955944046431,-7915916843081206753,7128040299479611567,2569984883088254448>()) {
                                          case 762085128:
                                             switch ((int)b.a<"s3m5qglodwzljo","cnSHW+sRxIkj0lufk0jOz5iptdr2z+hPHlzH6BIZi8E=",5007785444771826772,5280305734083883370,-1380230907963806843,-7166549874846776611>()) {
                                                case 359820052:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else if (this.a.player.distanceTo(var7) > var2) {
                                       switch ((int)b.a<"s38vhk9dsnmx0s","Mjt8YUsbzaIXVkvhy2asViyZrou6SA0TeD9ZcWj01z0=",-9114192275385082121,6084325258417861637,-4200459961428024435,-2144537807548904940>()) {
                                          case -1473601285:
                                             switch ((int)b.a<"s3hd68u0bjyszd","9dDl6JEiYL2cZ1z20BO8MJv+rV3Aeo50x86oDsgQwQo=",4619080192663983803,-1356169368734378946,-2449938107069227558,-1319695174812774856>()) {
                                                case 1335031785:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       String var9 = this.a(var7, var8, var4);
                                       if (var9 == null) {
                                          switch ((int)b.a<"szcag1mqunv7f","MTjq9n/u9X6sjVvHrk3K7J18qJfIPelhm1YhaPcC7LY=",1593360319962826695,-2354438571855218893,6301306251772528733,2205024844235216217>()) {
                                             case 1976128093:
                                                switch ((int)b.a<"s16ys9jiwdnavp","pb97eGXXMSneF4uBVO4dxuI3krLsVMRjMWRLXuGUgks=",-2010907990179191111,1201920337287242644,3617660389121824907,-4443746908951234609>()) {
                                                   case 160190587:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          var5.add(var7.getUUID());
                                          this.c.add(var7);
                                          if (!this.i.containsKey(var7.getUUID())) {
                                             label93:
                                             switch ((int)b.a<"s3ncmgtrrguphj","etIrOlkLIuFM5nQSC7uQLlyhi9Ipzus0pPvlN4Ny54A=",-5824962229284507237,-8922455351828823825,-1135807310169443117,-5054565648246238477>()) {
                                                case 811835457:
                                                   this.i.put(var7.getUUID(), var8);
                                                   com.yiyiaddon.d.c.a(
                                                      (String)b.a<"s1lvjcdcmu1197","kNh0qd4E0evjqP7A3iGsKB1qFfrmWx6WlOqqySr3AZD025DU91o=",1639351974154479831,358855723496457584,2997462213565017324,7396228753536337619>(),
                                                      i(var8) + j(var9)
                                                   );
                                                   switch ((int)b.a<"s3mva40zj52f1v","hKgMm/VPqCt4Ku6uH+Ihqc/jQ4WDMKunzOQ55wn3U+A=",-6047643047159439801,5827166879313837282,-8140784238294868777,1005999173286727668>()) {
                                                      case -1504059746:
                                                         break label93;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)b.a<"s3of3ywk00kk32","I8RmnTLko/SxEVWZ6VN81pcMZEa+Ay6Dbu+TcS+9PCw=",1421819122624086747,2934677127733300890,5264615410527110560,1821398853677471844>()) {
                                             case 1987595513:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       }
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var6 = this.i.entrySet().iterator();
                        switch ((int)b.a<"sjvvhgfja9b3e","feWyWMHTmYjfzAKCBZRs0WhmGBnDXGbrFih9AvAQ/WY=",5659999689267777357,-8023925393379962608,-5332928193689949647,5969683645335096093>()) {
                           case 575726920:
                              while (var6.hasNext()) {
                                 switch ((int)b.a<"s87w454zh1xo5","pnr0RrCbOG31bhIH1IjQbbqB1wDVP5Z/JoM6GRYKD68=",5886802482058305134,-3970959196704203846,6016727247547933005,3436436404798667081>()) {
                                    case -509309086:
                                       Entry var11 = (Entry)var6.next();
                                       if (!var5.contains(var11.getKey())) {
                                          label77:
                                          switch ((int)b.a<"s2vff63dujdkc9","mOdmLq1NoQLF0BD4ZbdcPiIi6B18v+WcUqM1ErCp+so=",6037103430346724216,7851634187502473815,-6023098762953373349,8607236524594569149>()) {
                                             case -1313831762:
                                                var6.remove();
                                                com.yiyiaddon.d.c.a(
                                                   (String)b.a<"s1lvjcdcmu1197","kNh0qd4E0evjqP7A3iGsKB1qFfrmWx6WlOqqySr3AZD025DU91o=",1639351974154479831,358855723496457584,2997462213565017324,7396228753536337619>(),
                                                   i((String)var11.getValue()) + ""
                                                );
                                                switch ((int)b.a<"s23vtdfwagtsbm","0e3isxMQG3SiXEIVLWDUPw34aRalsFRgym/7DlwEXbo=",7795890086101426898,-4945005888402003372,-8996522783552864602,5703067293210764007>()) {
                                                   case -291833599:
                                                      break label77;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)b.a<"s6rsgh0t8vhdx","rK7NWIdACF3Og1nvn1agbkxjXBB7Bwb8aOKPu/kiisI=",1031024063679620086,353229567137038558,-5113453419014556160,529946292323957785>()) {
                                          case 460218677:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (!var5.isEmpty()) {
                                 switch ((int)b.a<"s2bib0ntm15sdv","fAjhehea3a1obRLwIwJZf5FS4fOFAZnzZM5rPf7jsBY=",3272862294979832002,4445987753915072653,-3814560638599455765,-7594253794123686344>()) {
                                    case -568231622:
                                       if (!this.n) {
                                          switch ((int)b.a<"s1h5ut1imywagf","6quO5s1TY2uoZNenLDUbPYKWk7FEXDsrxQWN6tah5ZA=",-7065288169245452549,-1095130613267008021,-1522971670591644044,-3698390595505613430>()) {
                                             case 55530350:
                                                this.z();
                                                switch ((int)b.a<"s2gnodsgue6sej","icZKS/7Nfq6zp0ASGKauc5zfKoKnWnmFMX/bUCu72no=",-3717791322277811061,2267179775909377049,5590848191314475795,-365395152678377623>()) {
                                                   case 1817183706:
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

                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s1ttnywwm494i6","Fxa1hbzyjh6NVpmBbWKPFfUq3QUDLpJSfDtkW3+Z40g=",2947950398518471372,-6313763880403465949,-2662028245224395480,7689379888137984852>()) {
                     case -672110407:
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

   public void x() {
      com.yiyiaddon.e.a.c.a.a(
         (String)b.a<"s1lvjcdcmu1197","kNh0qd4E0evjqP7A3iGsKB1qFfrmWx6WlOqqySr3AZD025DU91o=",1639351974154479831,358855723496457584,2997462213565017324,7396228753536337619>(),
         (String)b.a<"s3hrd8hjxonuzu","hpHghA7DAW++15S5FzZ/Vy1V5OhYyPJQzibK+jOHK57w5kvQeQYXulDVZ6U=",655015639922306678,7714330326295580160,-6805043325180954435,-292794558867686918>()
      );
   }

   private String a(Player var1, String var2, List<String> var3) {
      if (com.yiyiaddon.e.a.a.a.a(var3, var2)) {
         switch ((int)b.a<"s36erzssw01uzu","sIEeeg9VgeLVoYC0ABXVZTwUn3EyM4y5CpMmQ1z0sTE=",3027757986252836495,7957141108099014717,-3027913303622563061,-7060135740616944138>()) {
            case 866575683:
               return (String)b.a<"saixwwa44p0nw","32HleLOH11xadG1J916Lo1TrokTh2Zf2Jyphai9LgrHA9g==",-3160548737733745920,7139107533082248986,1853835464142301569,-1651658578870300917>();
            default:
               throw null;
         }
      } else {
         if (this.a.o) {
            switch ((int)b.a<"s19negphf3fddd","b6XbTSMz4hRaCwE64kKG6gm8FKoN0cmOUpzw9/9u5fM=",3494682821027550926,-4989887101613303253,-8931955262696091859,-371307699713866454>()) {
               case -280817651:
                  if (var1.isSpectator()) {
                     switch ((int)b.a<"s19s82lfc61sol","DlSk25UaZhluzjwYjX9fNGgSZA2ffh2YWP+2I5xSq4w=",4465437875334233442,620311151110943410,8374522513613331160,1440737368721003031>()) {
                        case 840687299:
                           return (String)b.a<"s2wdseim27xld2","0GF4++ByN7BgbpLNJnUqqRLNdoImRjDQGtcgRLBYKpYoAw==",-6839612846565902572,1473378151789062320,-1901673802031378057,2099374577415417012>();
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a.p) {
            switch ((int)b.a<"sa2beta3xbxlk","lWBCwEe47n1nJrJLokOmh48Hle1i6G93OUGKCVm94pE=",7459529743498601930,-6531914577126108796,6401126754716408135,-3075330399890860539>()) {
               case 19991788:
                  if (this.a(var1)) {
                     switch ((int)b.a<"s2zp112tktfddu","LF8/7dqnuizclLFqrvMAz3Knd7xpA1TNFFpGGsc0n7E=",-2721340229102257535,7984768347175553719,-5268750579213355333,-8392367811751225832>()) {
                        case 1128353327:
                           return (String)b.a<"s11h4cf1f1v0hg","1TJYKgOpKSedZph2aodVGZ36t3K/mgyBHTKNpxL89K9Kd9Nm",-1691733273798193545,977917356108339240,-8444345117736130194,4627670614601821598>();
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a.q) {
            switch ((int)b.a<"s22ppr6x5v01t2","W4sNVdUWDsgL/cJal3Z+3rRqac0EGoheTnHDeMIv0Z0=",-3341437414578275489,8637264497738819115,-6017562357319098954,1234950038520279636>()) {
               case -525969822:
                  if (var1.isInvisible()) {
                     switch ((int)b.a<"s2h0w0ghg8afpb","wzytpTjJ75i4Z/qa0MHrii4Tus7IvE4vVBwe/KlYBJY=",5852288578794190468,-5362319722907502737,6170193817209096516,1111802752846948408>()) {
                        case 2067110152:
                           return (String)b.a<"s1ofe8qnewdt1k","GwX5NyWp6Y4dmTlLwNDo9BO/47kF5/4TcASDJR63KAo=",2338515427072599090,5567530673378738705,7886221876130724902,-7382237536760338881>();
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.a.r) {
            switch ((int)b.a<"s39pci6fewzjc6","tiH3kqdF4CTHeINAark+XrY9svpLtpAbxMR4+JnNtUw=",293884238560940808,-5610364867176431370,8821672270474194522,5408562183981722481>()) {
               case 1173878898:
                  if (this.b(var1)) {
                     switch ((int)b.a<"s2ta69w4lrtlht","LhHmLTEJENj4T/VHeTIZrTOhdlD+S0S4kqVbd1CES2Y=",5220605473279301690,-1057506695290101038,-1397592550251925309,1251050000950851560>()) {
                        case 673563931:
                           return (String)b.a<"s1jw8ihq8a99e3","R+2arklcQ7py5/HYz2nYrw9I2jGgudRFAu1EBYLIV2A=",-7858325356495617405,-8556502447925565585,2005038590199560851,-8241172482027501765>();
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return null;
      }
   }

   private boolean a(Player var1) {
      GameType var2 = this.a(var1);
      if (var2 != null) {
         switch ((int)b.a<"s1do8nsv9b98tm","/44kV5Gda6zTRkFwdVirnFAvlaxDSXQe37Od8OflPI4=",6276627369893958928,-7669989429459955149,-7758404056821050288,2444277937519591196>()) {
            case -657667719:
               if (var2 == GameType.CREATIVE) {
                  switch ((int)b.a<"shtnwsregrbum","E6QHwWqP0Yk+VKZ9FGUQ/ZH9+anEqfQ/dUNvg1QVAqo=",-8224499419717378221,-431714995480538432,3204896185009520171,2942151032224039285>()) {
                     case 708748852:
                        switch ((int)b.a<"s2vnls9fyvjt1n","kmyXWwDBD87RujF5sWCXH8qAovjmiAZ4+jTvhn4cCc0=",-581259226629092623,8489136126113480121,4286182950647404023,3859383828769857158>()) {
                           case 1871578334:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s21o2nb8be5jvy","OYXr/nEEvhyqT18YhLKTFu/NlVLbRcnG4lYZ6ac6oJU=",5690446242660114134,-6493152205362315546,8596005776894974888,-6143754426086038919>()) {
                     case 1412625201:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var1.getAbilities().instabuild;
      }
   }

   private boolean b(Player var1) {
      if (this.a.getConnection() == null) {
         switch ((int)b.a<"s2iv3cjli41poh","cx408txDDT+AJChcEMyVpTMs9SpRuRfTloRb2xFMc50=",1508719438112930836,8214414942680110081,4561374175514249527,-7610982644924050271>()) {
            case -675184733:
               return false;
            default:
               throw null;
         }
      } else if (this.a.getConnection().getPlayerInfo(var1.getUUID()) != null) {
         switch ((int)b.a<"s3t2ch50ll2una","ep6Qm/MxEUGUxQl4GApvgjlkApV6a9Qoe7V2MYa0LMY=",-6116604915690445339,-2579915652490134870,7189375728110675074,-7070530920398282930>()) {
            case 1169408639:
               return false;
            default:
               throw null;
         }
      } else {
         if (!this.e.contains(var1.getName().getString())) {
            label32:
            switch ((int)b.a<"s350qdsx8n054r","0Cd5n7B/sX5P6V+3mVKSynxokdSSZA3VhmI0Uu69S4k=",-2736525393764856035,-8588750678969404408,-7180230146834931715,13164398544657619>()) {
               case -616161580:
                  if (var1.hasCustomName()) {
                     switch ((int)b.a<"s2190xj6db2sgd","fa9qanowx5LsBJaeD4pcB5AnQnvsTkAQtCaPuS21kDQ=",-4548102460792384057,7796126445666433080,-8811442351786422397,4570475054362149509>()) {
                        case -2012559855:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)b.a<"sof9hao56wknf","9cNwv7AJ5/aloZ5BTEjXwA40DaN8nN1K6KdOlJGsB44=",1623314747152807685,5973513735663266233,-2566488607216389305,-8190919086022440149>()) {
                     case 253960760:
                        break label32;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)b.a<"s3lm4ug7yaqdn9","khr0ZoCT1W+W+x6s5VBmBb+nT+TH+A68WjydQPdPjTs=",-2924434634194655296,5287750273273364636,7117201151292123163,-1435841660274971575>()) {
            case -593478605:
               return true;
            default:
               throw null;
         }
      }
   }

   private void y() {
      if (this.a.getConnection() == null) {
         switch ((int)b.a<"s12ryxcq27b3g1","DTcBBkZZD4Frd7R6YEswX8wkYrnRDDbco8DW3Yu5Vvs=",1534011750910537841,8404031814893119742,-4573942379882002646,6927913209160475121>()) {
            case -2016733030:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var1 = this.a.getConnection().getOnlinePlayers().iterator();
         switch ((int)b.a<"s2an0jaz1p58sw","zy/d+WpdakokR5bVsDDi1XI3he4HpZdNXD3u2hMzdNM=",2719392973223479479,6102078066133113300,-1240763349967678897,4842971353076149906>()) {
            case -451914933:
               while (var1.hasNext()) {
                  switch ((int)b.a<"sgtz3mm3drnsz","cESMoscUWImoP9pAyej5DBy+1OIp8/4DCw6iG0DDNck=",-9161796521406561077,7246564876405814299,-6422756553489729218,7121978467185432527>()) {
                     case 2031311542:
                        PlayerInfo var2 = (PlayerInfo)var1.next();
                        if (var2 != null) {
                           switch ((int)b.a<"s2op1yabqlflge","i42IGMKsbiIyevGcw9wnWVck2AwsTiXl2omy47SWAA4=",-5999999919969497865,2780142511951111862,-3389277056697589953,1653867700223905414>()) {
                              case 1696269857:
                                 if (var2.getProfile() == null) {
                                    switch ((int)b.a<"s816cy436kkfl","xLReInAV5Cz0dXOvdYZa5XRn34+zbMJcPhpojDnbUPc=",-7488611945093575200,-2825385475043944073,7828617344809904636,-3595053352244959832>()) {
                                       case -464427410:
                                          switch ((int)b.a<"stnckbvlw7omj","TIsMosA1qETq1YRymHzSovO8iobdgfZBWIJJI0LHX+w=",-804238567686679313,1211786242133719147,-1183915392357893845,-4566150628727085370>()) {
                                             case 713566571:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var3 = var2.getProfile().name();
                                    if (var3 != null) {
                                       switch ((int)b.a<"s3hys7ny6jdckd","1GWL1Dzakr+5oimsuZG6kvRfumUKX1iwsWPlGalkFfo=",1657465588260051242,-7393456858783849837,-8225561687297279120,1815116430059797183>()) {
                                          case -2034447066:
                                             if (!var3.isBlank()) {
                                                label43:
                                                switch ((int)b.a<"s2bigbgn5mtayc","N5iByHpY0B1cqcqEeOmuwXKgBAAhjSa/WJF8m0GPPEA=",-8392435889549409526,-960357113335627621,5010456434871304029,-511326873583209905>()) {
                                                   case 59452705:
                                                      this.e.add(var3);
                                                      switch ((int)b.a<"s27hxlj07fkkjs","8jTPgTNzRsvoL9UaVcNJtDI8l1yNNj1rvRvUzkaYuEc=",2055862430224578064,8631023904187943906,5553116538644166762,7146859539155027305>()) {
                                                         case 717027965:
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

                                    switch ((int)b.a<"s3214vq5u5ip46","EZ6PwL8PhNFJBL8F5A3GPhqxVtpCz8rEOH54qJAImN0=",749912872903335767,6460388687592000600,3455944038544625440,5165241309571046444>()) {
                                       case 314242093:
                                          continue;
                                       default:
                                          throw null;
                                    }
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

               return;
            default:
               throw null;
         }
      }
   }

   private GameType a(Player var1) {
      if (this.a.getConnection() == null) {
         switch ((int)b.a<"s2rxcgpsvdv5k8","uNKRLfQ20tw6RpBFYsJrxZnD8TrVNTW4UgR5HBiFdRg=",2753959522175911326,8305512287841380859,4334737900010435627,5589290251469555754>()) {
            case 1755076723:
               return null;
            default:
               throw null;
         }
      } else {
         PlayerInfo var2 = this.a.getConnection().getPlayerInfo(var1.getUUID());
         if (var2 == null) {
            switch ((int)b.a<"s1hcxoyhr006rt","MxsJF3IXe7/E495CvaF+pmGqMVl8R6u7BD7bgSLNtOw=",-4259260318564756504,-7690497812373246126,1754342839563418710,8754889545119395889>()) {
               case -509055387:
                  switch ((int)b.a<"s47ehvpjto9a3","8SwlGnaOJx0I/kzKqyBPifFcq/zyC52v5xRYo6Si/Ik=",8954112743726462479,3233824429754079461,-2243562064803227106,4826270852494843638>()) {
                     case 922836459:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            GameType var10000 = var2.getGameMode();
            switch ((int)b.a<"sp98f635l7262","Dss9o957Wn9XFKx2uPabyoZUVdG4DPwIUu09nI6ZW20=",-7135256358939892486,-6013483972801133073,2736461815419160086,-4390505738193274968>()) {
               case -319812521:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }

   private void z() {
      this.n = true;
      com.yiyiaddon.d.c.a(
         (String)b.a<"s1lvjcdcmu1197","kNh0qd4E0evjqP7A3iGsKB1qFfrmWx6WlOqqySr3AZD025DU91o=",1639351974154479831,358855723496457584,2997462213565017324,7396228753536337619>(),
         (String)b.a<"s18325ok42z6nn","rmScs7rdmgVQpHpFDXmkY8tqJqhBqEbUknQN66zUfjed+8H9JdlvXlQ3jkz07vqb5OeKpQ4cOi/BCoaarN1LPxdn",-3876957596324111426,7041818298413841197,-8120434588440207661,3332846507515242188>()
      );
      this.A();
      com.yiyiaddon.e.a.c.a.a(
         (String)b.a<"s1lvjcdcmu1197","kNh0qd4E0evjqP7A3iGsKB1qFfrmWx6WlOqqySr3AZD025DU91o=",1639351974154479831,358855723496457584,2997462213565017324,7396228753536337619>(),
         (String)b.a<"s3r479qbsvckjh","h7Fxa1JheXJyIwqBKkX43J46INteK+N/GPeODBOYfdjNWGxGNN5sOPpz6w1ymA==",-6161483304807857922,42750644657700639,7674479708517976508,-8394249297347204955>()
      );
   }

   private void A() {
      if (!this.a.u) {
         switch ((int)b.a<"s29kcykehj9cck","pN6VWzZtz2W0VLAc4xvp3C4FB7a8vvIx4upek7V+SoY=",-7624834559955098970,4043188984689276134,-5876354868482398466,-3010867399238858723>()) {
            case -1239840500:
               return;
            default:
               throw null;
         }
      } else if (this.a.player != null) {
         switch ((int)b.a<"s1iyh9d3skv9bl","jTZIq2kyih0EybWTgfY3PmrARcsgbTAHDaL8c4COZ7Y=",7731923609592595519,-2715919322231523475,-6168359851062629198,-5145229103360097297>()) {
            case 893829426:
               if (this.a.level != null) {
                  this.a
                     .level
                     .playLocalSound(
                        this.a.player.getX(), this.a.player.getY(), this.a.player.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0F, 0.8F, false
                     );
                  return;
               } else {
                  switch ((int)b.a<"s2oz5zltrd5cbb","qJjgC8yEdfdKwfu+f+cIE6VkBvlEFLxWSgWMUhlBMuw=",7672479888953990033,-9148596358974855411,4662614003731787113,-8175180846067402176>()) {
                     case 765758645:
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

   private static String i(String var0) {
      return var0 + "";
   }

   private static String j(String var0) {
      return var0 + "";
   }
}
