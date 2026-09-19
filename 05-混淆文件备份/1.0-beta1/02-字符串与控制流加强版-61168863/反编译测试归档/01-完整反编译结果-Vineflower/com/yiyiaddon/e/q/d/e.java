package com.yiyiaddon.e.q.d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public final class e {
   private final Minecraft ao;
   private final com.yiyiaddon.e.q.g.a a;
   private final com.yiyiaddon.e.q.e.a a;
   private final com.yiyiaddon.e.q.e.b a;
   private final d a;
   private final c a;
   private final f a;
   private Consumer<String> a;
   private Consumer<String> i;
   private Consumer<String> c;
   private VillagerProfession a;
   private List<com.yiyiaddon.e.q.f.e> targets = new ArrayList<>();
   private int qr = 64;
   private int qs = 32;
   private int qt = 1;
   private int qu = 64;
   private int qo = 96;
   private boolean eW = false;
   private int qv = 2400;
   private com.yiyiaddon.e.q.f.d a = com.yiyiaddon.e.q.f.d.LOCAL;
   private g a = g.IDLE;
   private int cB = 0;
   private Villager a;
   private BlockPos S;
   private final Set<Villager> at = new HashSet<>();
   private b a = b.SEARCH_NEXT;
   private String zz = (String)com.yiyiaddon.m.b.a<"s21zw3t37vravr","5S6E8wS8BjWQRh2UpemJVVUPUEbVZC6lJQcY4Q==",-491601593022375573,-719219080233608510,943962912125049279,-3934290528229137854>();
   private h a = h.SUPPLY;
   private int qw = 0;
   private int qx = 0;
   static final int qy = 600;
   static final int qz = 2400;
   static final int qA = 100;
   private static final int qB = 60;
   private static final int qC = 200;
   private static final double bm = 3.2;

   public e() {
      this.ao = Minecraft.getInstance();
      this.a = new com.yiyiaddon.e.q.g.a();
      this.a = new com.yiyiaddon.e.q.e.a();
      this.a = new com.yiyiaddon.e.q.e.b();
      this.a = new d(this);
      this.a = new c();
      this.a = new f();
   }

   public void a(Consumer<String> var1) {
      this.a = var1;
      this.a.a(var1);
      this.a.a(var1);
   }

   public void d(Consumer<String> var1) {
      this.i = var1;
   }

   public void e(Consumer<String> var1) {
      this.c = var1;
   }

   public void a(VillagerProfession var1, List<com.yiyiaddon.e.q.f.e> var2, int var3, int var4, int var5) {
      this.a = var1;
      this.targets = new ArrayList<>(var2);
      this.qr = var3;
      this.qs = var4;
      this.qu = Math.max(1, var5);
   }

   public void F(int var1) {
      this.qt = Math.max(0, var1);
   }

   public void G(int var1) {
      this.qo = Math.max(8, var1);
   }

   public void u(boolean var1) {
      this.eW = var1;
   }

   public void H(int var1) {
      this.qv = Math.max(400, var1);
   }

   public boolean a(com.yiyiaddon.e.q.f.d var1) {
      if (this.a != g.IDLE) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nt6fj0j1lj2v","ZvnJRrPn9eYJQhSL07hMVO1x0uRHt3YM0IqiaSYj5gg=",-7242252808684657356,-4903645257328099806,3778977829342701262,-4323500110354234380>()) {
            case -1683386410:
               this.aW(
                  (String)com.yiyiaddon.m.b.a<"s172utv27kic3c","v1PLfcskRUrDnOcTW7xMbtIJMze2JHkOxwJTBbUJ/k0Di4A23Tp+nESGSFjqSQR8cwP6cbgV1onFijX3ywh2AA==",-8425518802537459430,684939070625797375,7672388418928195905,-5067485441288374853>()
               );
               return false;
            default:
               throw null;
         }
      } else {
         if (this.a != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s1zlhi4ser2gwu","//ReMtDZu+YpX1d1URJH9u/k882JziYTZyxURcDKzT0=",3672853310421587902,5507820343479798437,-4032121828838751651,4720372824359134971>()) {
               case 1681180528:
                  if (!this.targets.isEmpty()) {
                     this.a = var1;
                     this.gO();
                     this.a(g.SEARCHING);
                     return true;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2xhnzo1axb86g","2Rv6hAj9f3/PlkSNEx/9ORM4C5jqJDb3lZmlMoE63A0=",3305270011756277778,7642267000536575885,-3013380662670655277,-1782433499104669300>()) {
                     case -811578004:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.aW(
            (String)com.yiyiaddon.m.b.a<"s6e1moakonhu9","eA8G0EKoD80hDa+RlV4kEreArUEI330JHb7v27DsZMNxukB4yvAeZy7dwcTN2MKwZuADlA==",-7171017990476986892,1703096280268074940,-3827593347552071214,-7470037705785495469>()
         );
         return false;
      }
   }

   public boolean c(List<com.yiyiaddon.e.q.f.a> var1) {
      if (this.a != g.IDLE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n9kkrpn83pfs","SX2tpUsi+hkPAOS6HhU9FPa6ug+OT10rz01XBAVJP3Y=",-6535749534045790685,370206676880527707,-6113190525648011532,-8020906701074916999>()) {
            case -1935449952:
               this.aW(
                  (String)com.yiyiaddon.m.b.a<"s172utv27kic3c","v1PLfcskRUrDnOcTW7xMbtIJMze2JHkOxwJTBbUJ/k0Di4A23Tp+nESGSFjqSQR8cwP6cbgV1onFijX3ywh2AA==",-8425518802537459430,684939070625797375,7672388418928195905,-5067485441288374853>()
               );
               return false;
            default:
               throw null;
         }
      } else {
         if (var1 != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s1rstoam4tjbfg","2GzfdUn4qmkcjGjqXm0dG9WDxc5HPnCfVsj5OOUsqEI=",1129376156493149376,8224718769364317450,1591461707478007737,2760387763267312184>()) {
               case -100595181:
                  if (!var1.isEmpty()) {
                     this.a = com.yiyiaddon.e.q.f.d.PIPELINE;
                     this.a.p(var1);
                     this.gO();
                     this.I(0);
                     this.a(g.SEARCHING);
                     return true;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"szqhqtvseqjhv","Odlwxm3mSoAGPHoljmujupjgGDDsfpk+iURH1tCuWBk=",-8731868176256863295,6889872130022341499,6255760886430458007,8101633241829931152>()) {
                     case 1480968970:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.aW(
            (String)com.yiyiaddon.m.b.a<"s32bstdyxn9ohv","agno09HmmYfsjmS7aRPrIq4yWDJZ66fr+WeLqXQfA27wdBHP+J+VlgC0qOPxF7Ar08c2yIuf/Vio8Q==",5466054224235471965,8239600474780724167,4912318659313293050,5998701147128266691>()
         );
         return false;
      }
   }

   public void ag() {
      boolean var1 = this.ab();
      this.a.ag();
      this.a.f();
      this.a.f();
      com.yiyiaddon.i.a.a.cD();
      this.gO();
      this.a(g.IDLE);
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s92qeft3ho5cx","fDkqZaIr0n6lOnxzp0WQHBcouIm6dwyFS0avAaBiV0U=",-2904629046579159737,6861051591791300231,-7952767250886199627,6206177206570961934>()) {
            case -501045489:
               this.aW(
                  (String)com.yiyiaddon.m.b.a<"s2a16kqevr5c4u","nEpDp38JEE4thXb98F4VngpYa26yit2qxKjc+Txkdlbm3W5l2mRXWkxuQMqbH6rT",4824923024597536385,5393161029627210691,2594781391976546928,1724532769417785802>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2tfgrjp74pz6d","BDurjFdomO6wmBnlmyGeBQrpx+7d8D9+UROV4jpFBk4=",7724121935827810308,7394321133920340326,-5211320573546512931,1620319945517908443>()) {
                  case -1631835573:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void aV(String var1) {
      this.aW(var1 + "");
      this.ag();
   }

   private void gO() {
      this.at.clear();
      this.a = null;
      this.S = null;
      this.a.f();
      this.a = b.SEARCH_NEXT;
      this.zz = (String)com.yiyiaddon.m.b.a<"s21zw3t37vravr","5S6E8wS8BjWQRh2UpemJVVUPUEbVZC6lJQcY4Q==",-491601593022375573,-719219080233608510,943962912125049279,-3934290528229137854>();
      this.qw = 0;
      this.qx = 0;
   }

   private void I(int var1) {
      com.yiyiaddon.e.q.f.a var2 = this.a.a(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s23jwxdk8u6ksb","k85DbpuNc1LmCTxAk5yU4Fw2njwzpPmQykTF6hrHtxY=",-79079403909456597,-8044529107355990769,-5529182396937067170,7893694446788143406>()) {
            case -704177350:
               return;
            default:
               throw null;
         }
      } else {
         this.a = var2.b();
         this.targets = new ArrayList<>(var2.bs());
         this.qr = var2.de();
         this.qu = var2.df();
         this.a.iT();
         this.at.clear();
         this.a = null;
         this.S = null;
      }
   }

   public void ae() {
      this.cB++;
      switch (this.a) {
         case IDLE:
            switch ((int)com.yiyiaddon.m.b.a<"sfa9onczrxguj","pNNZReu63mdRFfsx+rXVL/3FRSrIjw3F8Zm4IsKWmsQ=",-6008070408990805792,6296871295233373991,-2677197155495832150,-3042178333510847548>()) {
               case -84060500:
                  return;
               default:
                  throw null;
            }
         case SEARCHING:
            this.iE();
            switch ((int)com.yiyiaddon.m.b.a<"s1kyvmmfhhxaj1","7rEGcWLCNpBGsO+kseH8DjXQemWYy0bMnV3rZ9UXiXs=",-4758311071448890727,-828416383832693214,-4448685196989458158,1670193340451819244>()) {
               case 2056216221:
                  return;
               default:
                  throw null;
            }
         case RESOLVING_WORKSTATION:
            this.iF();
            switch ((int)com.yiyiaddon.m.b.a<"s13b89rstila2m","U6uoZCvfiqMIOkFbgEJ8WIoEygwFi2BP3aq5/TAfPFE=",5586692180749356224,3854087349240300228,3299998483334039369,-2986039835013009496>()) {
               case 2028742796:
                  return;
               default:
                  throw null;
            }
         case NAVIGATING:
            this.iG();
            switch ((int)com.yiyiaddon.m.b.a<"s3tjzrhkrti7yi","MdRfcOqJIomPvGPsxbbv8cJsXR8ICyUjZtgQeMyD2Mw=",6477569013408227957,6863647250103253344,-4524729536335254768,-1003590477235148178>()) {
               case -995039920:
                  return;
               default:
                  throw null;
            }
         case OPENING_MENU:
            this.iH();
            switch ((int)com.yiyiaddon.m.b.a<"s250rcfeac0by3","bUfIL/B8y8PwLISt5zUefpw6Ib1MeYrJwRdwvBpO07Y=",6372429874720680081,7923278505633867949,-1560878321475115884,8886273357060988239>()) {
               case 162098272:
                  return;
               default:
                  throw null;
            }
         case TRADING:
            this.iJ();
            switch ((int)com.yiyiaddon.m.b.a<"s55yhjojmmc8h","mvDZ/+9pTzjbvowkM1dHdGWiSKbAOkyYCm2a160KzdY=",4457418130390841623,5441708876780675541,-3392844782511648796,-2880543700177357229>()) {
               case -1202308748:
                  return;
               default:
                  throw null;
            }
         case CLOSING_MENU:
            this.iL();
            switch ((int)com.yiyiaddon.m.b.a<"s3469extwtd2g3","ohj9QUYau9COLPvMosncDbaABi9hfY0W2cjSM74VJP0=",-5352652139812796974,2230314297285391633,3906793226004776035,-3781254273715537009>()) {
               case -2029677976:
                  return;
               default:
                  throw null;
            }
         case SUPPLY_NAV:
            this.a.iy();
            switch ((int)com.yiyiaddon.m.b.a<"s3otdgej66yq2x","FnV4m89aYIuwASGsy3/iGgpFt8MD22Rcq6/+EAnfdCg=",-5066084189004272588,-7893039698430873948,2072902055828993191,-4141556487346065633>()) {
               case -608658191:
                  return;
               default:
                  throw null;
            }
         case SUPPLY_OPEN:
            this.a.iz();
            switch ((int)com.yiyiaddon.m.b.a<"s21kdp641wzr2v","yLLFCwhGzg1KTUH1G8qR2K+SI1mB0J8SZxe7kxUkCgc=",-3128136653769124667,-6073309771522579308,3038573201306168089,-3016184916141090634>()) {
               case 818623889:
                  return;
               default:
                  throw null;
            }
         case SUPPLY_TAKE:
            this.a.iA();
            switch ((int)com.yiyiaddon.m.b.a<"sewhsdvnypyws","KKVDKQB4iug2aEENXTgldwYzse17aplhWvZZemQVkTg=",3133162714198903877,6709711320184532874,6440353253624378856,-7053527151582216877>()) {
               case 358210512:
                  return;
               default:
                  throw null;
            }
         case UNLOAD_NAV:
            this.a.iB();
            switch ((int)com.yiyiaddon.m.b.a<"svk29sozsm5z8","Asqk7rjH3VR4MArFNpo19LldtY2BBivBJUdLmdRpjx4=",8446606206372824326,68279399109696407,-6656082790980689539,-151637482010970366>()) {
               case -86918169:
                  return;
               default:
                  throw null;
            }
         case UNLOAD_OPEN:
            this.a.iC();
            switch ((int)com.yiyiaddon.m.b.a<"spe2lvnbgrxqk","e1nHZDWJzmlQIq2BEfL+bgN9RvZ2DbmVwEm0un9Dkjs=",-5660991437994936824,4723718341978133345,-6343215138957213722,-5873356280628651481>()) {
               case -129814467:
                  return;
               default:
                  throw null;
            }
         case UNLOAD_TAKE:
            this.a.iD();
            switch ((int)com.yiyiaddon.m.b.a<"s2191pmu86ep8k","MAlDV+5PnfQbxMzr6dq9FEM+aAomhqArE0ETgwpUXxA=",370453561113002269,4114746511555018321,-1998522349096209185,473488024456218397>()) {
               case 2059788987:
                  return;
               default:
                  throw null;
            }
         case WAITING_RESTOCK:
            this.iM();
            switch ((int)com.yiyiaddon.m.b.a<"s3bsau1oac0tte","MFY6tfrn4mbxaRdJ+cKFpIHRcjtxpwkkfrJQ/VHiaAs=",5301231455542162745,-3067189598640860584,-6537468583361602179,-6090684979896387754>()) {
               case 1798450163:
                  return;
               default:
                  throw null;
            }
         case WAITING_PLAYER:
            this.iN();
            switch ((int)com.yiyiaddon.m.b.a<"ssizg2joqouvw","o5DBP6u7Bu99RIqGniAeshzgz6Dq22x4ek5y7n2pkIM=",8972742546476024766,2489072379289106625,-2688133256545122262,3051084928526799580>()) {
               case 167133125:
                  return;
               default:
                  throw null;
            }
         case NEXT_TASK:
            this.iO();
            switch ((int)com.yiyiaddon.m.b.a<"s1c7eycmoshnm3","rn1fWqEO2msCoITjCtCvo1szlRR6DQpSgUGzvUcJ73I=",8569406441037648466,6555505489525750059,-7617920640384259210,-4064404800763621962>()) {
               case -1592562368:
                  return;
               default:
                  throw null;
            }
         case DONE:
            this.iP();
            switch ((int)com.yiyiaddon.m.b.a<"shssvkxxzc66y","FLFd69a+bdqiKCGa7QOF06fnc/r2qjqQ4CUnLCqq6Cw=",992452583865507886,-806675422021047027,-7249579296918205362,-8247569258730979854>()) {
               case -344210346:
                  return;
               default:
                  throw null;
            }
         case ERROR:
            this.iQ();
            switch ((int)com.yiyiaddon.m.b.a<"s1kboxfk92jahv","sUSiNCFexOxC1CeTcXEtBh68CkTvTpyiT0qzTLcaFIg=",5421297533115477743,-1595211232499703721,2813629048488000687,3004512449329378834>()) {
               case 1975587259:
                  break;
               default:
                  throw null;
            }
      }
   }

   private void iE() {
      LocalPlayer var1 = this.ao.player;
      if (var1 != null) {
         label89:
         switch ((int)com.yiyiaddon.m.b.a<"smxzag8hbuyox","lI4jlr9amnOgg582biaNiG9Lzv1C+4MHp86obIMqWsY=",1807701440673550273,-1534698048842251303,-589480685042422784,6982860465420863919>()) {
            case 722457479:
               if (this.ao.level != null) {
                  if (this.cB > 600) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1nphs6tsycjmt","lRZSL527vqQ9jzU8toFGcJqmsz6JwacStEpQl2maipw=",-184967984887707949,-1037158334850551562,8714539695149697048,1700986187805097177>()) {
                        case -1772606965:
                           if (this.a == com.yiyiaddon.e.q.f.d.PIPELINE) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3gexddjgbgk52","HUCgf4OetYND9wD4aUw3AfNW9Wi/K5tlfyf87L3ISQU=",5080618820355508328,-6956633986362688843,-5049608657027229533,3116336443793235049>()) {
                                 case -1410779655:
                                    this.aW(com.yiyiaddon.e.q.c.a.a(this.a) + "");
                                    this.a(g.NEXT_TASK);
                                    switch ((int)com.yiyiaddon.m.b.a<"slte2of4iiz3o","DPPhvRjC5JA5p/goxFwBYaYtDh3P+sbrc3CfEOXtiTk=",-7977128141255008382,-6798428297309549277,939983769449368360,-5428433805977672216>()) {
                                       case -1069252965:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else if (this.eW) {
                              switch ((int)com.yiyiaddon.m.b.a<"s20ss64ti7hxuv","6lJe99tUERm/j8+Nv+5cFeU4Z2GLN8h5hoCAcfDFbHA=",-3743613757078026420,-1158145885257117070,3061317825635183275,8126736575993738664>()) {
                                 case -1321531752:
                                    this.a(g.WAITING_RESTOCK);
                                    switch ((int)com.yiyiaddon.m.b.a<"s3actzxwm45zli","zvsLBfdRnbwsJ6CQIE0O/rZMz3YQmTgpkUUSjV5GEjc=",8128802234293832913,6609592569880039769,299401377114672016,4069833579285720174>()) {
                                       case 1460814414:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.aW(this.fb() + "");
                              this.a(g.DONE);
                              switch ((int)com.yiyiaddon.m.b.a<"seihn7c7bkexe","Gl1TqeCGY0iNLObsQXV1TAj4w4zxBh8vjvTZS7yISzA=",7271678323378760633,4338466517590539123,-7099006170071041443,-6691340313516526176>()) {
                                 case 1827703355:
                                    return;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  double var10000;
                  if (this.a == com.yiyiaddon.e.q.f.d.LOCAL) {
                     label78:
                     switch ((int)com.yiyiaddon.m.b.a<"s116h0tgf872vl","43iPnV2R1ASKR5RBFUHN8B6ZqY9V9C6dNlqnVbRAB/8=",-8390499913462790185,5687400487407421815,-8049757708698787429,-3729281803883660009>()) {
                        case 1179976455:
                           var10000 = 16.0;
                           switch ((int)com.yiyiaddon.m.b.a<"s22ijsnef6iwao","+gZE02Zac1YeKhoPhPYzv7sY1fOAf7UlqtTRizN2MwA=",-7661355303082358023,5902589392650371011,5962031019991566053,7054910101782249674>()) {
                              case -979673605:
                                 break label78;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = this.qo;
                     switch ((int)com.yiyiaddon.m.b.a<"s2vy20j1142zy8","woCfL2QDSha2n+vGZZmDKe3px9rygPszYP1SG6LTe9Y=",-1897781040048373418,1545383621996360983,4128292844120671533,-7266185808367625676>()) {
                        case 1509257012:
                           break;
                        default:
                           throw null;
                     }
                  }

                  double var2 = var10000;
                  List var4 = this.ao.level.getEntitiesOfClass(Villager.class, var1.getBoundingBox().inflate(var2), this::d);
                  if (var4.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"ss3safztd37xf","x2IHm23A166P/I50v+jZgcXXpb1iWRcah0N0ZWxfakY=",-4510176740362497958,6445614693144129961,2955363632151545986,3518029384111170699>()) {
                        case -1433123338:
                           return;
                        default:
                           throw null;
                     }
                  }

                  Villager var5 = var4.stream().min((var1x, var2x) -> Double.compare(var1.distanceTo(var1x), var1.distanceTo(var2x))).orElse(null);
                  if (var5 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s354auzksz2gmx","nEpSRY6MdvscGS5xfaTYytWWupD5uqx8P+D8qmD3MAc=",6131613018815332038,-2816375287810147445,1910305065553384162,-5993317277037091105>()) {
                        case -459251052:
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.a = var5;
                  this.aW(com.yiyiaddon.e.q.c.a.a(this.a) + "");
                  this.qw = 0;
                  this.qx = 0;
                  g var10001;
                  if (this.a == com.yiyiaddon.e.q.f.d.LOCAL) {
                     label67:
                     switch ((int)com.yiyiaddon.m.b.a<"s2uvm6n6hhaj33","2uUBqx4T46+Jc0QWLSgC+8MffXFTHBF3AQqsqjj/rg0=",6753929951519316168,-1716394548611025805,-5333093028008949043,5528746089388774428>()) {
                        case -314704034:
                           var10001 = g.OPENING_MENU;
                           switch ((int)com.yiyiaddon.m.b.a<"s2v3ju2ddf0ne5","zd/mIvf2cwGe9G8T5Y/YPdUGfNAExq19CnvmUdD8x0k=",7999252474330343978,-7426669543370882025,-5555264071940772596,1397073916808210194>()) {
                              case 1884336355:
                                 break label67;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = g.RESOLVING_WORKSTATION;
                     switch ((int)com.yiyiaddon.m.b.a<"sy9rc37nl8t3m","SpORUxvzvad2QDguEQ/2Htp6UWfT204pbzyAzY6HuRA=",-6371791287217170172,2504198055446304286,-3525940824725892410,6447960065055059201>()) {
                        case 2110331096:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.a(var10001);
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3ifmvqfb3vjzq","j+WSHJQzGuOTDp5mULmg32rXCVvkGV9W1Bg5NC7RNpw=",-3286616356691337219,5586756080484019902,-5192228649610320853,-6434258640852543542>()) {
                  case -881832640:
                     break label89;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.A(
         (String)com.yiyiaddon.m.b.a<"s1cfztpgfid58","udmLSBzJNLwbAJYsKxhJkYWWFJVCdtH0jXCuYI9+nNGGPVbvbwDz/L4w",2901465641473317104,9033820733713556933,-8778514218023335751,7627385455303518879>()
      );
   }

   private boolean d(Villager var1) {
      if (var1 == null || !var1.isAlive()) {
         return false;
      }

      if (var1.isBaby()) {
         return false;
      }

      if (this.at.contains(var1)) {
         return false;
      }

      try {
         Identifier var2 = BuiltInRegistries.VILLAGER_PROFESSION.getKey(this.a);
         if (var2 == null || !var1.getVillagerData().profession().is(var2)) {
            return false;
         }
      } catch (Exception var3) {
         return false;
      }

      return this.a != com.yiyiaddon.e.q.f.d.LOCAL || this.ao.player == null || !(this.ao.player.distanceTo(var1) > 3.2);
   }

   private void iF() {
      if (this.a != null) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"s2brtw6p96bdui","8F08ciqvee+S6bQ9AYitYATwFH1Euo8Fxp7b00OnxWQ=",5177405302043251294,-1050097162622333866,-3061070732261241579,-5916986218876337787>()) {
            case -938244429:
               if (this.a.isAlive()) {
                  Block var1 = com.yiyiaddon.e.q.c.a.a(this.a);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ms5xxmiucefp","x1jA1QvRW809xDq/0RZzTGD3f1XEqpOiKJPfrTZWgP0=",5258069184143726329,-3251320563592177077,6630971359420967713,3581066137617048133>()) {
                        case 1663708297:
                           this.A(
                              (String)com.yiyiaddon.m.b.a<"so1rwd932xobp","SldjZp0b0a46yorTTZwvEmKk9S5KSOXPtWtB+6pOMrJJxT+y3u2JBdYD8uNsMg==",6495060778785296577,-2991426976105364253,-2862674708693851838,-4549141811146532503>()
                           );
                           return;
                        default:
                           throw null;
                     }
                  }

                  BlockPos var2 = this.a(this.a.blockPosition(), var1, 1);
                  if (var2 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3257dea80oiju","Nhd5bDo6cUYNyENVrQ9a9s0/2+wQ5m2VFsA0tRipWMM=",8639253134911399903,-123882472042632997,-6144773956894205299,8215353778360449858>()) {
                        case 408764960:
                           this.aW(this.fb() + "");
                           this.at.add(this.a);
                           this.a = null;
                           this.a(g.SEARCHING);
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.S = var2;
                  this.aW(var2.toShortString() + "");
                  this.a(g.NAVIGATING);
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3mcu8vdqrb9m7","6k46rErMsKcTtenQshxsqzTDZx4dp82cJKwlgXShjgU=",3883537886441816010,-5831119891924080460,7774311330255503580,5241790835012491072>()) {
                  case -779888608:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a = null;
      this.a(g.SEARCHING);
   }

   private void iG() {
      if (this.cB == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bebvtbpdl8es","63uX6rtNVtLCJsNiy3Jyjk7wqZ4O1RQYmU5rQsGMWJU=",-4456868053199962430,-4205502223139111969,1298865743778001357,-5090717065653887805>()) {
            case -1334374680:
               if (!this.eL()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1t460cj598y9a","zumcfkpFrklcTNbusB9TNId+uOU3ZQaiYJGs5ob0FRo=",-634271649315480693,-19987037246593885,-2532757702837115803,2129737912433184456>()) {
                     case -1955938796:
                        this.A(
                           (String)com.yiyiaddon.m.b.a<"sj1m356eup5fu","qLcNxWQJfuRaiVRyX0WJOIduUy89xUqVjgcJ5LExksF529mm/DKPAQ==",-5202859495927389195,417876264672385638,6026710695082318646,-1658413915918672490>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"svcgq7elj9d4b","C78FUDgeeDhgYMOop3In99XlTsqCUnLILjaXs/3pl1U=",5117599194538740908,5855154144352072728,1503018508761052115,1317819697616932002>()) {
                           case 323708935:
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
      } else {
         if (this.cB <= 2400) {
            label91:
            switch ((int)com.yiyiaddon.m.b.a<"s1birwkjxwrwxn","zpxrOjmoymlxtp5QQAreqo8x4ufBRRzSO1zf/B/n7yY=",7114830064771665577,-5880064901519460309,5666600002887003091,8903493895328948910>()) {
               case -1932578620:
                  if (!this.a.cn()) {
                     if (this.a != null) {
                        label84:
                        switch ((int)com.yiyiaddon.m.b.a<"s3p5xap0jstd7o","o6OpTYelvciCArpyCvZEH/LEnJKQAwCUeo8EW+MDnyM=",-9210638959933688636,-1749787169637510601,836950573497145217,-4333954544878666381>()) {
                           case -773353517:
                              if (this.a.isAlive()) {
                                 if (!this.a.w()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sorawy3z6odx2","FuhiTGNhC0rw0AkHUAGkZLMWiJpqonqKeQt2IBx3Mf0=",-8734258142206465723,-3192958056081467785,2216382522123999620,-580228115196025093>()) {
                                       case -126223089:
                                          if (this.cB % 40 == 0) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1sjlnacrivpsh","IJRijveH8rsLngAL68gleUPILnGgCbEXGlBDx9WmFn0=",-5974683684039102577,-8436896280745237601,8111477358300071491,2802779297043322762>()) {
                                                case 1960619029:
                                                   this.eL();
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

                                 BlockPos var1 = this.a.C();
                                 boolean var10000;
                                 if (var1 != null) {
                                    label67:
                                    switch ((int)com.yiyiaddon.m.b.a<"s25c7ku81m5mj6","r7nrQ1SyFdeYBSM2qNpDhjbsgCN1GCoRYrgzJMNm3ek=",7552838391921661098,-7385400207785967669,-3441695856713082501,2867238621106005310>()) {
                                       case 1036622493:
                                          var10000 = this.a.b(var1, 0.5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2iluhiuzyr3ev","T8hTNQ3OG+4Vt4X/e1e3Ez704SIK5SFh+MTfwlhxJic=",-849238060440416237,-2924429072885122525,177707826841133451,4155518215367515334>()) {
                                             case 62076744:
                                                break label67;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else if (this.ao.player.distanceTo(this.a) <= 3.2) {
                                    label70:
                                    switch ((int)com.yiyiaddon.m.b.a<"s15u1keftoazc6","icxBnHyiUmdTO8nZJ6btPR3AEqzclC1d4KhajHDEMo8=",3919103836091249198,-1448484123180868542,865611558946141627,6703092627485431565>()) {
                                       case 1018549376:
                                          var10000 = true;
                                          switch ((int)com.yiyiaddon.m.b.a<"s15nf5e5xcb04q","Eo8y8UHJOmi072AMUNmOEGIxJK4o/aOb66XrKeCrBoY=",9127908046422991940,-2148537924948640309,5689121110827981686,1313191488968577917>()) {
                                             case 2089064889:
                                                break label70;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = false;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2v9qzkudubbcm","cpRyAqGExCpBstGGsDv+/hbvStewrffVVYT+U9zE1Mw=",8637919211250709956,3405836262947644903,-223393967169219494,-4615780029726709568>()) {
                                       case -449443340:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 boolean var2 = var10000;
                                 if (var2) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2c93g2xyxk8f3","KjWr9pXckpmc+5H7kukDt37jePDyWpBcPwi5bij+YYY=",5547552698966140976,-555686156873852049,22165475813106328,3517740734614266939>()) {
                                       case 135666441:
                                          this.a.ag();
                                          this.qw = 0;
                                          this.a(g.OPENING_MENU);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3vxo2epaylxt9","tmX0K7OSvDUFAcrI2J8Q2QxbfbVeWAkIr0VgigCljW0=",-7582996070791947823,1428275323072455594,2395205555293933572,-3145595850177167292>()) {
                                             case 1180591144:
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

                              switch ((int)com.yiyiaddon.m.b.a<"s2i6wz3p3cbflg","sUKcToobo+pLt3JXUKUd6j9BtUypCENJeCFuXzHS8OY=",5985762765586159694,-7729355704884819761,1790923494476071334,-2578874386866097462>()) {
                                 case 2108797659:
                                    break label84;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.A(
                        (String)com.yiyiaddon.m.b.a<"s8iapdnx1wnwl","j9GkmbXwKKJyA41nhCzyRgGyAtkZoTf2SFt7a3BAiVBEc3mq",2825437542247816066,9076050619591840958,8390430688696968055,3249963402998274512>()
                     );
                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"srms640qqk1t8","pxSUzL3ghs7r5iXIBuHl2T1MTCZDnea6mKHgWLhpTEQ=",973767074893583332,8430968087912091672,-3511659019409453070,2460230570958220920>()) {
                     case 1193989308:
                        break label91;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.A(
            (String)com.yiyiaddon.m.b.a<"s3q7np9x927iu1","pcoY65ZzOxbj6c9wZ7pfolakyTuc8/jrXBQNYiUk+fUqc97/TusTHefH",3748968790265405870,-6045762030645852749,-5792474630552772906,93928620700145861>()
         );
      }
   }

   private boolean eL() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13fh0kfac2adf","+0sCu7cn/MpfP2T3Q0xA2ULv7bvDrWwXElUgN/nFTw8=",5754593769311958888,6419484730670371124,3193288829597109565,-4003796396241029909>()) {
            case 1970567969:
               if (this.a.isAlive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wu1701rkqkb6","xGsoJ5qZjFMzH8C67qibKylo+qxTImk7LHvSB7P5M9c=",2503884894939898111,-1233416982756159516,433846055453885840,3977986142995928876>()) {
                     case 1413298577:
                        return this.a.d(this.a.blockPosition(), this.S);
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   private void iH() {
      LocalPlayer var1 = this.ao.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pvuq8mhi0y18","t+Ajlrp+B7cgh+BTNLcqedlfrnHS2y5i0pV73FM9lJo=",5036226643818699782,3714993899222630034,-7927525346237651060,-2725201101511049802>()) {
            case 983702214:
               this.A(
                  (String)com.yiyiaddon.m.b.a<"s3poea8ih2al9s","2jwqK0Oc2Q0UOUfaNl/rWs3pSB61hykPya0q6A1lJtCGt1y2",5074875592969890094,4564759834733006520,-1993541658555097983,4578617578438474492>()
               );
               return;
            default:
               throw null;
         }
      } else if (var1.containerMenu instanceof MerchantMenu) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cvyv3yt4nnf3","XYoN9n8i6PkyY1mE2SaAmhzJdOGrkjJlENargdFEDJI=",8680965016384756453,-7774672033091022614,-300833594274302614,5758785368342295557>()) {
            case -1571501216:
               this.iI();
               this.a(g.TRADING);
               return;
            default:
               throw null;
         }
      } else {
         if (this.a != null) {
            label85:
            switch ((int)com.yiyiaddon.m.b.a<"s3jg5146judlrf","u6xhm1ZrJWlLkAYuuYPaz9aMQtqn5CRDvMc7hJfyIPI=",-6799129814639736720,-8745554540927421631,-2638856109147645187,497659907827528862>()) {
               case 1312237280:
                  if (this.a.isAlive()) {
                     if (this.cB <= 100) {
                        label78:
                        switch ((int)com.yiyiaddon.m.b.a<"socw7zppvl0rr","lbm47raZigWpXUudFzjWuuDXbO5+zxhV+EddXt0iXQs=",-5350296533846683263,4676202255838241394,9061272997928702360,4819485239675824829>()) {
                           case 1614758546:
                              if (!(var1.distanceTo(this.a) > 3.2)) {
                                 if (this.cB % 15 == 0) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sqz6qbqhrz2tl","QsTQybYs9xiKxMxv7l65YczDcvehC8tSptnEEoUJ5UY=",-2600705734324949562,2228508632520982772,-2846622317230262583,3725813306675738647>()) {
                                       case 571434388:
                                          this.qw++;
                                          if (this.qw > 4) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1whddjars5yal","vT29ya4iA17zvZaJ51wL50Pxi4wBRYIR3VZAPE0LBzE=",-9217939603714316278,7414900825117871477,-2111880950693806232,-6977305416927099185>()) {
                                                case -104554173:
                                                   if (var1.containerMenu == var1.inventoryMenu) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sc534mi893127","cikZdbJuEHwz3BWFzUvwJDhaGAOSkBR4vR87GyJRTG8=",-6555528157942641190,-3391386062458227867,3543286061716079103,4647116868665747195>()) {
                                                         case -1281542766:
                                                            this.at.add(this.a);
                                                            this.a = null;
                                                            this.a(g.SEARCHING);
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

                                          if (this.ao.gameMode != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s5q3rxki37380","uSuJyepCoq4HhkBvXtiNGVqE7ryk8zTRbzdCK926+Wk=",3753628686099839882,-982781521991212067,3452876501810177659,3719497178022816820>()) {
                                                case 121808336:
                                                   this.a(var1, this.a);
                                                   this.ao.gameMode.interact(var1, this.a, new EntityHitResult(this.a), InteractionHand.MAIN_HAND);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s14aqdauxreh9x","kyDL5Jy2Lk+Nts+Gja8sbb5gU3kkbrLZ8vSJ08Iz8HY=",8650185584127504712,8211621895722372753,-6595790255304619163,602214523476007197>()) {
                                                      case 715745100:
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
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3rdpilz0422ns","jp7wZ9nZED5jt/6odpgM6mMUBL3jW1WXsrpEHHJkvzk=",413824134768842178,-7612373783092481215,-1858949668945779904,-8267780662510020708>()) {
                                 case -2113802239:
                                    break label78;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     if (this.a != com.yiyiaddon.e.q.f.d.LOCAL) {
                        switch ((int)com.yiyiaddon.m.b.a<"stpubodktqmzc","K8d8wpi9TQpM6My/EnMSXASzujl/ycVRy2r77z4QY1I=",-8940345366467997579,-5258101111270074836,4068627368098706972,1330303411126600557>()) {
                           case 1766182451:
                              if (this.S != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ew6wipqw045i","FAxCIghI6a3Z2R0r6knQgXeR4z+hYzlKHVeyxMYqy3M=",8771527953916503428,2423945182262875080,-2415996570042443098,-3413333455698552496>()) {
                                    case -520835582:
                                       if (this.qx == 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1mqwtcznv253l","oocJnJpw5sWSZIDPCTGwm5jJfwJSkMDzQLRcA59geps=",-5674995732573860899,933101111575804097,5110280445712077709,-2530142501934092501>()) {
                                             case -770368768:
                                                this.qx++;
                                                this.a(g.NAVIGATING);
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
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.aW(this.fb() + "");
                     this.at.add(this.a);
                     this.a = null;
                     this.a(g.SEARCHING);
                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s20ry6k40iwq4g","AuKBLVECNstz80DLPXCuMLuLpAcl3E9OL1HY3AhnK9U=",-8349297453704244916,-7402848465857322898,-8393578428385986147,-5707525477850078469>()) {
                     case -1266446848:
                        break label85;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a = null;
         this.a(g.SEARCHING);
      }
   }

   private void a(LocalPlayer var1, Villager var2) {
      Vec3 var3 = var1.getEyePosition();
      Vec3 var4 = var2.getEyePosition();
      double var5 = var4.x - var3.x;
      double var7 = var4.y - var3.y;
      double var9 = var4.z - var3.z;
      double var11 = Math.sqrt(var5 * var5 + var9 * var9);
      float var13 = Mth.wrapDegrees((float)Math.toDegrees(Math.atan2(-var5, var9)));
      float var14 = (float)Math.toDegrees(-Math.atan2(var7, var11));
      var14 = Math.max(-90.0F, Math.min(90.0F, var14));
      var1.setYRot(var13);
      var1.setXRot(var14);
   }

   private void iI() {
      this.a.iR();
      this.qw = 0;
      this.qx = 0;
   }

   private void iJ() {
      LocalPlayer var1 = this.ao.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ya3uwlswczs","1fkV44CPtAyGr8kmpv+ULLWlvZI3/cSl0SrFFa9STc4=",-628396430812099567,-1769845710044182645,-3289100420561532434,-8899552607559316134>()) {
            case 139027463:
               this.A(
                  (String)com.yiyiaddon.m.b.a<"s3poea8ih2al9s","2jwqK0Oc2Q0UOUfaNl/rWs3pSB61hykPya0q6A1lJtCGt1y2",5074875592969890094,4564759834733006520,-1993541658555097983,4578617578438474492>()
               );
               return;
            default:
               throw null;
         }
      } else if (!(var1.containerMenu instanceof MerchantMenu)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uve1oanzouyq","85J4V0UpaPw20TQxoL6JqTXnSudWiGhoM/LwXHWKm94=",-1571585042553590927,2461521392421954632,1165885548080041292,8056427712420921712>()) {
            case 1510679599:
               if (this.ao.screen instanceof InventoryScreen) {
                  switch ((int)com.yiyiaddon.m.b.a<"s37qrwia2r0ol5","bbHracUvzIX9qVjDLmPUoBxgg9L+bRqn5ZONzimHMRw=",-3315214873169987331,3205374824848595132,3738277921536908505,5277577338968098676>()) {
                     case -1807165391:
                        this.A(
                           (String)com.yiyiaddon.m.b.a<"s3vmrecpbx5jjm","unxWIJkt3E6WhebYZHFMW+ATmd/38ip798dy8/u8hKh1s/GW+VhxZgyzFacbNDOQOEoUOOU+YzT3mg==",306304059861864338,8579515394027726525,8261355134225607147,7278698931113245015>()
                        );
                        return;
                     default:
                        throw null;
                  }
               } else {
                  this.aW(this.fb() + "");
                  if (this.a != null) {
                     label43:
                     switch ((int)com.yiyiaddon.m.b.a<"s2dfqdbst9w45","S7C6pvEQzxQPNRtWCWxPwbcdeSvO+Wg4IDOCp7Roh+g=",-3662748183276234499,-8849275421099022559,5634581058325562682,-7209172252955795495>()) {
                        case -1850569312:
                           this.at.add(this.a);
                           switch ((int)com.yiyiaddon.m.b.a<"s3lyv1lvptwffh","cv3oJg5pqpfXkkLIDSaOGbmMYsQWH0vv4utk8jMUlCk=",736502752031148775,2139653749248689810,-8381424795392925995,-5702942319899884732>()) {
                              case 1448212678:
                                 break label43;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.a = null;
                  this.a(g.SEARCHING);
                  return;
               }
            default:
               throw null;
         }
      } else {
         if (this.a != null) {
            label63:
            switch ((int)com.yiyiaddon.m.b.a<"scjf2lw29ychj","xYSgphUM2sSDUftXG0piYanylsIK9svu2hBbK9aBdoU=",1686886480912050495,3421067417011519451,-4835431500922809166,1800165983695807194>()) {
               case 403311340:
                  if (this.a.isAlive()) {
                     this.a.iU();
                     if (this.a.e(this.cB, 200)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sfk5211irujlb","CuA83TfysEBiL7VjC34gyFH4l7uaH2UuuLebyGctQ90=",-1854558881524731077,953857961576931703,-4172174099709280280,-7557904281146282740>()) {
                           case -1447145407:
                              this.aW(this.fb() + "");
                              this.at.add(this.a);
                              this.a(b.SEARCH_NEXT);
                              return;
                           default:
                              throw null;
                        }
                     }

                     switch (this.a.a()) {
                        case SELECT:
                           this.b(var1);
                           switch ((int)com.yiyiaddon.m.b.a<"s3u5wimbdkrvlw","Nig3gdMWp+0MJlQvd7N3S79bcFj0jXQLiTTH0y6GmDM=",322566911986803776,8057051275752197985,9154984793776147247,-3744147546467703361>()) {
                              case 1624002692:
                                 return;
                              default:
                                 throw null;
                           }
                        case CONFIRM:
                           this.iK();
                           switch ((int)com.yiyiaddon.m.b.a<"sxmwal1atxln5","DqTiDk577SKBsHeJENlMC8PuzIyJomF0cDXz7WbUh30=",2470592150113422498,-5936503902656413892,-7324816814040590261,7732995224732305992>()) {
                              case 1662071178:
                                 break;
                              default:
                                 throw null;
                           }
                     }

                     return;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3b3egtsvysklx","nvkq+wy7jSosTcxm4yIOO96l58Ezg2YqqaF7tpzn2oM=",8535572532307036866,2326701820747044142,2628039285868651304,7989407775021150980>()) {
                     case 68836223:
                        break label63;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a = null;
         this.a(b.SEARCH_NEXT);
      }
   }

   private void b(LocalPlayer var1) {
      if (!com.yiyiaddon.e.q.j.b.eQ()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ved27idx7n0n","UjtU9/g1rxBC4IJKlwGGxruREN0OvNfOJ8kWFILGSKw=",701299946896913400,-2953291225157765756,849183414454230345,-3664900439696225229>()) {
            case -1847875938:
               if (this.a == com.yiyiaddon.e.q.f.d.LOCAL) {
                  switch ((int)com.yiyiaddon.m.b.a<"seg0wi2d305yc","PwD0J6qvqNTlu7ZciE6NKCre/+Ye4KfRifvce2SQeCU=",1360047353358869947,-7289475646015646905,4506491934952390068,8709804781659027713>()) {
                     case -745351810:
                        this.a = h.UNLOAD;
                        this.a(b.WAIT_PLAYER);
                        return;
                     default:
                        throw null;
                  }
               }

               this.aW(this.fb() + "");
               this.a(b.UNLOAD);
               return;
            default:
               throw null;
         }
      } else {
         MerchantOffers var2 = ((MerchantMenu)var1.containerMenu).getOffers();
         int var3 = this.a.a(var2, this.targets, this.qr);
         if (var3 >= 0) {
            int var8 = com.yiyiaddon.e.q.j.c.a(var2.get(var3));
            if (com.yiyiaddon.e.q.j.b.dg() < var8) {
               switch ((int)com.yiyiaddon.m.b.a<"s1pojt0gseou9z","DuftrJQaMJBvonT/+vnYo88Q9Nrtngi+wjQmVEZSEGU=",-8524718330739169274,-4288208972109802201,-3915343442409083290,-5398529662472914254>()) {
                  case 354706314:
                     if (this.a == com.yiyiaddon.e.q.f.d.LOCAL) {
                        switch ((int)com.yiyiaddon.m.b.a<"stdbtgw6ogmcg","7ddm1hSPKQfOtlAHdsNWUliDwFor2UGsfUjwFBOjoR0=",5587744419771635567,-3913378238752346098,2018869843591175949,1588482894291681056>()) {
                           case -1775546851:
                              this.a = h.SUPPLY;
                              this.a(b.WAIT_PLAYER);
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.aW(this.fc() + "");
                     this.a(b.SUPPLY);
                     return;
                  default:
                     throw null;
               }
            } else if (this.a.eM()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3dn9f30q04lxt","O1YI84TCAFuqYLKxqE1W35/nUeE9NplrcsQsA8kR+mU=",6225822223799094991,-7065255076854211975,4517486435637408177,5307737488143641071>()) {
                  case 938551704:
                     return;
                  default:
                     throw null;
               }
            } else {
               this.a.a(var3, var2);
               com.yiyiaddon.e.q.j.b.L(var3);
               this.a.iV();
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s2p0vcyc7jypyu","fNA/Hph8WkFALzz197+1nMSucOvFDkOx0z6J+Ow+JTQ=",6338632935177916659,4171924214049657497,5684719921208576365,3837110213433311504>()) {
               case 1402180033:
                  boolean var4 = false;
                  Iterator var5 = var2.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s13u82hqb4d6ft","R//GEnu7u8bAixSnZ3ohpQX1+W177q2YOLeIRRKHjX0=",5494074721735029808,-9032078345819600541,-7395681398911694645,-5550221783855713220>()) {
                     case -1403755285:
                        label107:
                        while (var5.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2bs3anh9m6bxk","+ihIyGBZ06T20eEs9CN1ZJnaDjaExGtxOMRwjir/Hcw=",-8144448219186313836,-1152456760372925038,-6175026161915382453,2082968880268036476>()) {
                              case 1434413640:
                                 MerchantOffer var6 = (MerchantOffer)var5.next();
                                 if (com.yiyiaddon.e.q.j.c.a(var6, this.targets)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1silgo3cqeka6","X/G2lSw9KD2pUJuuxhfRCA5oTwBMoQLVnXQFJKUz4oY=",-502525406118456193,5233838401668593956,3120546249008929299,-1302109782597660943>()) {
                                       case -1860706258:
                                          var4 = true;
                                          switch ((int)com.yiyiaddon.m.b.a<"stsxqzc1k7sz2","rZ6VrtIbDDVS0RnA6G5MhHCP+tlwYDvrWVut77CLYAw=",-2492993805632892911,-2410157634707915819,-2316088681860735646,6443081183100620312>()) {
                                             case 1897407868:
                                                break label107;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3dc6d6ti5x3ij","6uyTxd5YdGAEWSA6JqmBPBCv6rehM9z9x0fBGfvVl9k=",1856030121554058510,5492160251311651472,-6537493108198102193,1296875918247278133>()) {
                                    case 1405493040:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var4) {
                           label62:
                           switch ((int)com.yiyiaddon.m.b.a<"s1shp2snaug38e","nAXHR3esVXNkEvQImXAD/brb2Nvw8Odg2+f0ZRqZqfA=",8029178566630157969,8626217499512728297,-6536189171856444703,-4655042651616988362>()) {
                              case 903823354:
                                 this.aW(this.fc() + "");
                                 switch ((int)com.yiyiaddon.m.b.a<"s1f3yzafxfgcl8","Dkbei44GjbylbNfQ/A26GhyadXtr5xruxurhPio/KSE=",-4164534471540461907,2294397234949763014,-7068707978197166043,-7798081371269902718>()) {
                                    case -935327048:
                                       break label62;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           ArrayList var9 = new ArrayList();
                           Iterator var10 = this.targets.iterator();
                           label68:
                           switch ((int)com.yiyiaddon.m.b.a<"s2l1d16vaj6kmi","j9YUB/WSYMgsQyvH6JNb7vn6CdWRKxcHgKo6/KFG9vo=",4326755436120991166,-6678195474549648336,6426418141132835896,-4061686644214802527>()) {
                              case 1014016421:
                                 while (var10.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2xktipsxdcwc3","V0ObTMfN5gus5Mo4oGPRCVrn3GoN+xRHCAC/ddLGCu0=",527887151304691047,3537150434359460074,-1712539761571391180,945957893989433435>()) {
                                       case -1973244682:
                                          com.yiyiaddon.e.q.f.e var7 = (com.yiyiaddon.e.q.f.e)var10.next();
                                          var9.add(var7.fe());
                                          switch ((int)com.yiyiaddon.m.b.a<"s142df2qkl1fu4","owPCJHsdPIYqM0FnaFGbzcLh3DWfu4yJXUSkjnNNZBA=",-3922008180849306327,-5044546202931647260,-1248744847892524637,7242891473108087368>()) {
                                             case 1266685598:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.aW(
                                    this.fb()
                                       + String.join(
                                          (String)com.yiyiaddon.m.b.a<"sh3enf0wmp3pj","hU8MVuzoQJRzwyhbUE8acBAK1xvPyrE8lwqjbipN",-845238623188842139,-266325092484950700,-6199651786054970476,-3361212622260271909>(),
                                          var9
                                       )
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s370ffewvtmkwp","+rpsO6EUmuI2bx5I0hJxzI3Ehu/PTNxxcVy31ko1pmg=",98830029150787417,5718578970181345624,-7331825890296900523,-3556700451753918940>()) {
                                    case -412204837:
                                       break label68;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.at.add(this.a);
                        this.a(b.SEARCH_NEXT);
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

   private void iK() {
      MerchantOffers var1 = ((MerchantMenu)this.ao.player.containerMenu).getOffers();
      switch (this.a.a(var1, this.cB)) {
         case WAITING:
            switch ((int)com.yiyiaddon.m.b.a<"s2f54zmy8jpxla","+WHXaTAHAPTEz5t3ObYoDv8jqbMQcZhU9VmMzZWulIY=",3935714881515717396,2837192113722049611,7229304471661226157,3851081519072686044>()) {
               case -1838165639:
                  return;
               default:
                  throw null;
            }
         case SUCCESS:
            String var10000;
            label44: {
               int var2 = this.a.cY();
               if (var2 < var1.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wfxk042vcxnf","0qiNkzpF4YrDOTTU1bOCugVX6LGzJBK1L2VcpFVWl28=",-7347459015335388399,-3517824823663331446,8981210826892718373,-7220500999274593766>()) {
                     case 839332927:
                        if (!var1.get(var2).getResult().isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1d0iugskxtl0h","WsnqzwMSgjZ5/Zc2XVM78h9L+b4iM45TAYLZm81aKb4=",4487276013549566725,-8445091399226184892,-4845940492346574985,1757981816097050821>()) {
                              case -188660442:
                                 var10000 = var1.get(var2).getResult().getHoverName().getString();
                                 switch ((int)com.yiyiaddon.m.b.a<"s32b7stasx5qoj","9lcJfg4XJxxB73+acUaW/ixwgWLYSmJdrmwsQnE4oVw=",6580310032378313469,-8743051436839318242,-2930772553248518363,7391440058779518617>()) {
                                    case -508148133:
                                       break label44;
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

               var10000 = (String)com.yiyiaddon.m.b.a<"s24y74h9xeip9f","0i3uCS7qk8N6V5dGVPv6tP1OX9TPxZe1tp8KTZN1lFQ=",-7612479950968972326,4178694163476430755,6037046310171630020,7838408245148912595>();
               switch ((int)com.yiyiaddon.m.b.a<"s2l6kpbwqtaij4","tGZVXYZKUBKapHlQPEfRxEStr8cdWgoOqOonUVjjoBE=",-2334079743107377266,6088388251656535085,7138754538996412305,880565942539353980>()) {
                  case -1121303760:
                     break;
                  default:
                     throw null;
               }
            }

            String var3 = var10000;
            this.aW("" + this.a.da() + this.a.db() + var3);
            this.ao.player.playSound(SoundEvents.VILLAGER_TRADE, 1.0F, 1.0F);
            switch ((int)com.yiyiaddon.m.b.a<"s1jfqj9mio5b6g","F0CpxruT8qeJjJDQH1GTbLV2W5mP5MYpLWdw/kYmJzM=",968593487709028504,991507276536662218,-652779881613776358,1614585932761089975>()) {
               case 286823517:
                  return;
               default:
                  throw null;
            }
         case SKIP:
            this.aW(this.a.dc() + "");
            switch ((int)com.yiyiaddon.m.b.a<"sin6r5qmu83rq","SXgfqm5oqVE/x7ZuMQk0O/N2IK6oVmNoL1I+UPZCHBc=",7642042276134226855,-557011977014252521,2445951292197238720,4777465039087046442>()) {
               case 2138858197:
                  return;
               default:
                  throw null;
            }
         case RESEND:
            this.aW("" + (this.a.da() + 1) + this.a.dc());
            com.yiyiaddon.e.q.j.b.L(this.a.cY());
            switch ((int)com.yiyiaddon.m.b.a<"s2u9ywbe8pw30b","0qxI9UYYh6wdGqTGWmuLpiNoyYyeZsNNMRaLstCzwpY=",-1148115528252311532,-1819148535665534091,-8882881642240848346,1633739436239649753>()) {
               case 679066598:
                  break;
               default:
                  throw null;
            }
      }
   }

   private void a(b var1) {
      this.a = var1;
      this.a(g.CLOSING_MENU);
   }

   private void iL() {
      LocalPlayer var1 = this.ao.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"seg9w3od1pn2u","WGaTDrXWofNi7vmG/Rb5/IED4C7jTgn2+9R0uF0xB8I=",375989639480300697,-8984161951423752423,-3846625122714986156,-2190514063883872038>()) {
            case 678836157:
               this.A(
                  (String)com.yiyiaddon.m.b.a<"s3poea8ih2al9s","2jwqK0Oc2Q0UOUfaNl/rWs3pSB61hykPya0q6A1lJtCGt1y2",5074875592969890094,4564759834733006520,-1993541658555097983,4578617578438474492>()
               );
               return;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         if (var1.containerMenu != var1.inventoryMenu) {
            label47:
            switch ((int)com.yiyiaddon.m.b.a<"s1tkk8c8njbvpm","0jXwtCYVc6pyzRe47MaDMU47TDE+hE0lq7Hdm2SUw6E=",-4008750287769211767,-7173081126584843638,8375230114581426467,-1115945057937437685>()) {
               case 737683645:
                  var10000 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s190ya5jgvnq99","Dbm5qBUQTr73RULJrpP2HhlOTaif61Ih5PEfL5CaoKI=",8430626144487350436,805249505760955304,-1540843062102597900,-5713787718209907159>()) {
                     case -1730823133:
                        break label47;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"sw9s3s1qna3mq","m5K75cDMbbQGzdY21sjg41GXV8f82RIJMUOyxx0UXNE=",-3665177318331044207,-7185745570813217473,-1675078136801439618,5042273563473136670>()) {
               case -2071731134:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2 = var10000;
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s2jry11neptze1","N9tQGYdNA2iMcEoi29p0gM0PP+hZ/6ujh9/jvfU092E=",-6571253242807364515,-5120402439873233404,4357858024457302416,1726046123060287181>()) {
               case -1618258264:
                  if (this.cB % 5 == 0) {
                     label39:
                     switch ((int)com.yiyiaddon.m.b.a<"s17t1uc32ex7s4","50RcmvxVlhhR7zOEgx78Fwc8gnqzzWdX5KQ4t22BxAE=",896055182963288534,-6504330711346728159,6906826795023693476,3764946604233227620>()) {
                        case -1038298034:
                           com.yiyiaddon.i.a.a.cD();
                           switch ((int)com.yiyiaddon.m.b.a<"sel5b6f8166ne","a1+tvMRcprudUyVFB2FwZ7ly7A/LN4JVxlWGsl69RMU=",7469484682612613374,3323663176973330467,3757256656392466732,2761683399540072083>()) {
                              case -1469509365:
                                 break label39;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.cB > 60) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3u159afc79khv","IERzvXaQV0/aDu/kQnZqZ2dVnrwbBuuaLOHmuESaoGE=",1756918755499803461,-312753156763571875,-4747031737172311936,3789660232874647094>()) {
                        case -1553601197:
                           var1.closeContainer();
                           switch ((int)com.yiyiaddon.m.b.a<"s22z4dr4to9c74","skLsKEMpenUm1uNhED0nUGQdaUMht2fdQfF7yrHeUQo=",9039220292210653413,9061111617527104045,2321222810458440767,-1900995768772189953>()) {
                              case -1499122691:
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
         } else {
            this.b(this.a);
         }
      }
   }

   private void b(b var1) {
      switch (var1) {
         case SEARCH_NEXT:
            this.a.iS();
            this.a = null;
            this.a(g.SEARCHING);
            switch ((int)com.yiyiaddon.m.b.a<"s23vcn8xkhl8p4","KDo6YGrMz45ih3T1jwQsY3V70zFeYAp9L9WlMiP5P9o=",-8679747299345491106,-1509420061226246648,1952489275589543844,-5583905132551889461>()) {
               case 1345924278:
                  return;
               default:
                  throw null;
            }
         case BACK_TO_VILLAGER:
            if (this.a != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s26mejqefl3nao","je6vxER1UXq0BZ2QNzwrNd9wIRJTXpwOchhy/RPlBwc=",-7391977036528193484,-6487209908853340302,2915323415891070623,-7979425667388308243>()) {
                  case -1969674616:
                     if (this.a.isAlive()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2mbsltn2yvydn","A5FM8ltVTqJotQZVZrHMGEeGlPT/pl+n5U87g9a3PJA=",8342861966335704505,1585878417322417262,2562322401743794784,7273246214428119824>()) {
                           case 1361079410:
                              if (this.S != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2fqf38haj8i4x","E9jgXMmHqkUH8+He2u5/ERKA686bpUE3HYD6WbN1uD4=",4259679496979325060,6701639317098426702,-5471251284444107823,465565450646280939>()) {
                                    case -1371617646:
                                       this.a(g.NAVIGATING);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ihq6vtgwvntx","LfiXza6/oui00z06oHjKgJK0m8ALonbfEQWzv3Q9yEQ=",6507218768946419155,5409763964762024990,-3843434878269340858,-5814831361360342478>()) {
                                          case 37352783:
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
                     break;
                  default:
                     throw null;
               }
            }

            this.a(g.SEARCHING);
            switch ((int)com.yiyiaddon.m.b.a<"s3fhcwp6mst1lk","PDPDZk7JBiph90fhHzVBRvtTP+3VkF0/b8dY0zzd/CQ=",-6178413446211158756,-8920171331404008340,3811952948739466386,-6710198766205105904>()) {
               case -248220133:
                  return;
               default:
                  throw null;
            }
         case SUPPLY:
            this.a(g.SUPPLY_NAV);
            switch ((int)com.yiyiaddon.m.b.a<"s14u5xgtfe02a2","wBBLEpms9V+59TCbA7wyj83PZbuBx3Ay2mwi99Jtmlw=",-3699593016408966791,-3830111989922699757,589044792400462239,-7083392439851096187>()) {
               case -1054473686:
                  return;
               default:
                  throw null;
            }
         case UNLOAD:
            if (this.a.d(this.targets)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1xk6rb4j3hryi","1s+KlB2XOCp5XOMMTvVsoF0uCiFtej3z5J5RK5GgW5A=",-8630366284011895269,8788576895481768808,613377068151614758,-4578237386603053474>()) {
                  case 1029556124:
                     this.a(g.UNLOAD_NAV);
                     switch ((int)com.yiyiaddon.m.b.a<"s2e08ng01od8mh","vUB0ypA3QpP6bzDi9o1/0/PwoxtaMBvx2xCDXutAo1w=",-2571434794738064934,7789534138700756084,-446014879432363723,-1678880849300146823>()) {
                        case -504763749:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (this.a == com.yiyiaddon.e.q.f.d.PIPELINE) {
               switch ((int)com.yiyiaddon.m.b.a<"s21cy1788ic3nq","ugOhYeDDb89xYx21JHWA2pTUNy2hx08KZetdtvaGYRU=",751036186449097993,-1482631350813060135,8953058662625639236,180200891197808290>()) {
                  case -486637309:
                     this.a(g.NEXT_TASK);
                     switch ((int)com.yiyiaddon.m.b.a<"s35kn4ijwhxtnl","m+0fr8wipWrgNfWXuV8YwCinnhi/61ZhYvjxQpNqwME=",1784903280111966992,2961577870641621273,-3835465098185509537,5481367572679226894>()) {
                        case 1707290347:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.a(g.DONE);
               switch ((int)com.yiyiaddon.m.b.a<"s2row6yz1a7xt3","K1GPNcXlqmNyosEOcSDyPeouczExJFstFE0jHaqlEbQ=",-205462162262790000,-6953597606801208492,8698632995458531443,1846802370669275684>()) {
                  case 2046890720:
                     return;
                  default:
                     throw null;
               }
            }
         case FINISH:
            this.a(g.DONE);
            switch ((int)com.yiyiaddon.m.b.a<"s3qhuoc8xqnsdt","31rVLhr9YgbQh72NurqY1+TxGoqshmGx8vvoZ5vgkto=",107178907558374276,4015339029276538970,7838939429438955150,4354758180549126944>()) {
               case -1290039839:
                  return;
               default:
                  throw null;
            }
         case NEXT_TASK:
            this.a(g.NEXT_TASK);
            switch ((int)com.yiyiaddon.m.b.a<"s281wdjfeog4fs","AmeFh48fmemgUcf1WF9sRIZinvVIPwCyk1BWcXc8GRU=",-5428736332218617183,3436737658784565826,2716377650408438193,-6927377049957766012>()) {
               case -223009558:
                  return;
               default:
                  throw null;
            }
         case WAIT_PLAYER:
            this.a(g.WAITING_PLAYER);
            switch ((int)com.yiyiaddon.m.b.a<"swonkq88xc3sk","s+rSsp+Z70bEWAFgO3GPYHryZ3FBm0jKfLMp8TSBnss=",-1070343114572884830,4188395170334649168,6073604993881833576,-3813070132529311485>()) {
               case 1853727241:
                  break;
               default:
                  throw null;
            }
      }
   }

   private void iM() {
      if (this.cB == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1he2ux4ytgvzi","zO3JdonbrzFOt5AvlsiRf/I4xgHP0JPHMCwOvAK3g1w=",2620572489413863775,7480656615054473010,-3912207756657391876,-9189140725495417831>()) {
            case -169589438:
               this.aW(this.qv / 20 + "");
               return;
            default:
               throw null;
         }
      } else {
         int var1 = this.qv - this.cB;
         if (var1 > 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s2qkh74hx9o1w","nGyufRCKgjp90TLY7z6FmoGoNnxcZ5whrAZ7A2GAYWU=",1505675402278427691,-897160267954638055,-8477351861743360483,610890848641985232>()) {
               case -1943963609:
                  if (this.cB % 100 == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3c9gbdj312wn0","uVhkQoGkNJa170Qi+GV8kQSn+WiK16WlV+Y40u9yT/M=",4624526562348715464,-3228242038092734403,5266296351541485063,6734587533614684276>()) {
                        case 566278154:
                           this.aW(var1 / 20 + "");
                           switch ((int)com.yiyiaddon.m.b.a<"s11vrxj8h99xgz","yBnvUnS3fddEZ4CzKUK1CvIhOTjGOtyrmhbxg2Ydzuw=",330318407069971323,-3757571816249540552,5546805049887684851,5922067364828224321>()) {
                              case -974055115:
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
         } else {
            this.at.clear();
            this.a = null;
            this.S = null;
            this.aW(
               (String)com.yiyiaddon.m.b.a<"s2wyph162bi03d","Dq/Xx+twV10LmZjXe1KNgbpIuq+fiQx851b3aS1SGxlO0bObpA7P/WfoqLrSihAL3Uc/lAIoUFiDvTW0v33Br3b8qqDPZufk",-7028674639757756692,8536074496704674689,-6999452279971221630,2801776286883924736>()
            );
            this.a(g.SEARCHING);
         }
      }
   }

   private void iN() {
      LocalPlayer var1 = this.ao.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s15hr2gvrtykhv","lWxUb2ja95uA9xFHLxxEAwqroSpBqXAbqBbzV2/Azn4=",5246057922097883535,-276122478049700173,4573954657764344404,8274355023189307814>()) {
            case -1308644038:
               this.A(
                  (String)com.yiyiaddon.m.b.a<"s3poea8ih2al9s","2jwqK0Oc2Q0UOUfaNl/rWs3pSB61hykPya0q6A1lJtCGt1y2",5074875592969890094,4564759834733006520,-1993541658555097983,4578617578438474492>()
               );
               return;
            default:
               throw null;
         }
      } else {
         label100: {
            if (this.cB != 1) {
               label81:
               switch ((int)com.yiyiaddon.m.b.a<"s3mgs4rnpzl10f","0eBxw62w02irrckN1K3aE8bstwJDKMgKqb3ot91Rjp8=",-9126679902903386636,7227056006900770472,-4986495476701864710,6675528296664687800>()) {
                  case 1285365614:
                     if (this.cB % 60 != 0) {
                        break label100;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s14f2xwvfim3gs","q1mNVO5gUixQTRpmcwAv1acMOvrvb0SMimFTAEPrAVQ=",-4280215490231452456,-536525237461461060,-926929636548131612,7847141292755102842>()) {
                        case 1092185983:
                           break label81;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (this.a == h.SUPPLY) {
               label75:
               switch ((int)com.yiyiaddon.m.b.a<"s1tw5qz1u79e2r","eGJZsqzTm8XQo11EmN+X1Blxef1G1Nh9OqBaxHwsekE=",-4917411703876379389,-3955774427240158824,9023767068040914771,4258726261429578459>()) {
                  case 125157115:
                     this.aW(
                        (String)com.yiyiaddon.m.b.a<"s2ln7f3ftqjosy","eQfdWMvorQnbWECARHeGWGcdfl0iTDGC/VMHq1ouRNQO8yBfVg9ClWYbeMgh/oAZq9f+Pxt1l/vjAvLB8n0N8Xj+FQoRYWL6yRE2qZvD",-9203906046533589467,6057803199891731339,-8075322109620627404,-8139457847265383434>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s2f756o7nx8ljb","LdLbTNTesVRBuuy/zGJzwk7RAuSp97xygFfrYzD8Ex0=",-3309521675712271852,4071383849867026421,1620624424845048430,-5385829231545623393>()) {
                        case -726247205:
                           break label75;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.aW(
                  (String)com.yiyiaddon.m.b.a<"s1leekjyt1vwrr","wqJBFKJLlp/3ew++L8fRDWLg9+vHQVTx1FpOJrTviEjZf4Hjzu2mpkhsn+Qf9oH7Xk2XUdd4WbPIRd0jhbuAke4f3qdacg==",1398598916008962169,-3447360770740318726,8792636264236463189,6232691324632233375>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2c77orbdwlw4q","bqSoq1v0XaJsrpcx1bBURB5IaeP1iVespsu3kFPGXJo=",8484924797945256913,-1080041804583955580,-8976195773488345554,-1253068422220070971>()) {
                  case -1444760427:
                     break;
                  default:
                     throw null;
               }
            }
         }

         boolean var10000;
         if (this.a == h.SUPPLY) {
            label69:
            switch ((int)com.yiyiaddon.m.b.a<"s18nheujxs1g8e","54q4e4D0SSwK2iQbrsRRDtyjBQE4xTSGnOrf4bBfpRE=",-6040823404541490992,6419087572260891838,-601014475213866101,1911936767387114990>()) {
               case -1014303970:
                  if (com.yiyiaddon.e.q.j.b.dg() >= this.qs) {
                     switch ((int)com.yiyiaddon.m.b.a<"s23381hltwf8ax","1EyETI8POwAt7zV5KtGNO7+njL1FvpD8ow3/a2slDG8=",198726787344564677,-8742247386892456245,8626968343642266564,-5864267874380197789>()) {
                        case -1136790650:
                           var10000 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s13ffc5as8tl3r","43PIslwV8M6pTiG1RhjOO3I0ffdeIZ0G97Pl7RYEDSE=",7309496628249851785,-3362376508915376612,-4061774422640350053,-7564736407969146855>()) {
                              case -552837472:
                                 break label69;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = false;
                     switch ((int)com.yiyiaddon.m.b.a<"s2q46tb9d4363m","9ReIH5cFdcmaMb+D2ydGNTKKsk4Jl25lsxNQa31ea/o=",-7783657746296167771,5713645593884154275,-4260725551242179200,1043167191667487687>()) {
                        case 2002129290:
                           break label69;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = com.yiyiaddon.e.q.j.b.eQ();
            switch ((int)com.yiyiaddon.m.b.a<"s1i466s9nw0d2s","TXu0HkWwymWqEv7L3DD4tSu6MgxSOurWxmForXGMF6M=",3334541451850121554,-6980468297019969763,-6782701643438889832,-6601019960658516798>()) {
               case -1459428194:
                  break;
               default:
                  throw null;
            }
         }

         boolean var2 = var10000;
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s3s3jmdsg1cejg","ofbrkSb2vmynylLHhT2mXWUtxV6TRVhElzL/tDSWkqM=",5882593800246823714,2520254414137797326,-4080870280624878454,-5708414207819713410>()) {
               case -965815234:
                  if (this.a != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s60c5v5c3zp30","vRueJFf2X/oviI2O1czTOKDtVAqx+7fgKZzLsRUMvYI=",-3294137007090706115,-1339554942723685603,3153325140644869220,4153762045279139779>()) {
                        case 923705917:
                           if (this.a.isAlive()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s28nvxardmf6hh","lYSJFPBGX1XX8NclqT5abaYriWbeix0YlMiWsJXXd28=",-2282606916001188852,-5602636134552804324,5690041473578399804,5659314776300466814>()) {
                                 case -632325173:
                                    if (var1.distanceTo(this.a) <= 6.0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sfcilncy0b5yd","MbHxFv4W0l4UWJFcfm9jhgGoqDkCRtCfCHJ0mmVoRYU=",-247702216770373850,-1398474402504020635,3705187319385550653,3266626676449452318>()) {
                                          case -1577863332:
                                             this.qw = 0;
                                             this.qx = 0;
                                             this.a(g.OPENING_MENU);
                                             switch ((int)com.yiyiaddon.m.b.a<"s2dibnpsgaqnhy","dkGyu4ZDIcHhd4XXe0ejeZmDDxiiOVuXBUdDAwzIw4Q=",79840938411684951,1007246290775860864,7843458344541503713,1390263222215422026>()) {
                                                case 652469029:
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.a(g.SEARCHING);
                  switch ((int)com.yiyiaddon.m.b.a<"s2mz60tsjh7utf","kQvcuvb35u/ji7O4d7YVuXYoS9iZoXUJbr5hivHuCng=",-8736206016045612968,-975921916546327696,3670770712374342812,-9148527218726600772>()) {
                     case 1565497644:
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

   private void iO() {
      this.aW("" + (this.a.cV() + 1) + this.a.a() + com.yiyiaddon.e.q.c.a.a(this.a) + this.a.cZ());
      if (!this.a.eK()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3p9dx6cfmfce2","ZCu55Jwdl4O64egcSmcgTZKouzb2vSILlO2qZI00wqc=",2448713902437751945,-8431807413599485191,-8412138483305262470,-4159152446605766892>()) {
            case -2041580424:
               this.aW(
                  (String)com.yiyiaddon.m.b.a<"s1sbdaajmwptm9","Bbvg/tdGDc8Znu1Q/eCID6lnaetSnx4JL0VPFAPv3soC4B8ZNgm2TMxTo50OiRdLK28qhKxi85PeuNX71oXz7UaOantrdd6e",305952117476309018,4534647911980566995,-1503357456826428900,-3493372257328965948>()
               );
               this.a(g.DONE);
               return;
            default:
               throw null;
         }
      } else {
         this.I(this.a.cV());
         this.aW("" + (this.a.cV() + 1) + this.a.a() + com.yiyiaddon.e.q.c.a.a(this.a));
         this.a(g.SEARCHING);
      }
   }

   private void iP() {
      if (this.cB != 1) {
         switch ((int)com.yiyiaddon.m.b.a<"sagjj52rwz7vo","wUAeIfIGUefZ6fmfSf5dCOauy7TkKlCxp37L2UnrnFQ=",-6728158330008961716,7639922189379346554,8948124253979395780,-2824256649815432286>()) {
            case -830607203:
               return;
            default:
               throw null;
         }
      } else {
         String var1;
         if (this.a.cZ() > 0) {
            label30:
            switch ((int)com.yiyiaddon.m.b.a<"s217gdaunvrort","QrxE4C7WvdT4hVYQLMQd0uQPiLZhiSfwznCfPZdUop0=",2190014019811471881,4328913898701089203,-4779053322016228157,4697883399743351091>()) {
               case 982434882:
                  var1 = this.fb() + this.a.da() + this.a.cZ();
                  switch ((int)com.yiyiaddon.m.b.a<"sgs7sv1rbwf46","xqC7bWzwkwBFSROSvKvigM+ljUs20j4aSHHpM0w6rBM=",3290629503921651026,3052610788106432084,4997882021569953259,5115184928641903458>()) {
                     case -792538720:
                        break label30;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var1 = this.fb() + "";
            switch ((int)com.yiyiaddon.m.b.a<"s3224xvrmree71","V/X8dkNBbQvLoi+eKeK7e/vGFj8p9OvQ4BdJ/7lfbgM=",4872745904259544252,2406535593623650159,-442386268556290725,-2045322682024384024>()) {
               case 712233668:
                  break;
               default:
                  throw null;
            }
         }

         var1 = var1 + "";
         this.aW(var1);
         if (this.i != null) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s1wfwdezkiw9u6","lL4AiQ6pmDH30x42HB4Wgflc0tLrJ7TdXisEgy2AQr0=",6833612347123944091,-8092279290227314298,4558749029830872445,344321638817413041>()) {
               case 886866870:
                  this.i.accept(var1);
                  switch ((int)com.yiyiaddon.m.b.a<"s39daqnhpuhu5x","cv2B6fPw9vLilPs0w84pI+bCcwt7bibCqCCqSQQMZSk=",-7010978041239169020,8066991811354547054,-2127220734774303351,-2123560779957006710>()) {
                     case -1068886752:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(g.IDLE);
      }
   }

   private void iQ() {
      if (this.cB != 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3iqlau5yctuai","wsy5+1C1UFkxdbCDFfcu8Jg88EsNaFatXskKCUV3VPs=",-1048227482247657929,6414191672174602855,8678067530172960138,9094727417488552470>()) {
            case 1760693704:
               return;
            default:
               throw null;
         }
      } else {
         this.a.ag();
         this.aW(this.zz + "");
         if (this.c != null) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"s1mnozr5veoxsj","qar1nhaclVVBdhaecsWEH6Ai/K9jrk76W7SiqD/4lLM=",2721012919182874001,5595158869243077817,-4668030818523147364,2897827155799473613>()) {
               case -740349125:
                  this.c.accept(this.zz);
                  switch ((int)com.yiyiaddon.m.b.a<"ss8q59mye3y5f","uei3lqlaYGd4GiRX7P4EGFWX44rCCzpLRijjzxihCYU=",-4788979873909382914,-8007546793722209120,7469821400654420546,-4578208001602607693>()) {
                     case 324454895:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(g.IDLE);
      }
   }

   private BlockPos a(BlockPos var1, Block var2, int var3) {
      if (this.ao.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n8boeynz0bv4","Pp+9XS5zsMGg0Hthly3KNvThx/NG58q11uOO2X/modo=",-2567352212460493433,-6281341072891862807,7273615813710680763,-3081393345130482094>()) {
            case -1388483570:
               return null;
            default:
               throw null;
         }
      } else {
         int var4 = -var3;
         switch ((int)com.yiyiaddon.m.b.a<"s2pk6559hvnshu","Zq/71oCJys2u9IL6mEaPJohYKEBcn9AgJZLeIdzjgMk=",-587081083518205028,-1732344433343656841,-2854649990726257362,-1913592138409614060>()) {
            case 1758370060:
               while (var4 <= var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s26g4mcsn2j7x2","YGRfylhfMI1vdYzKbBCjuPGBWJWyFZ6w6TfUwhPbIjo=",-8445334126491322997,-5094972164552338233,2434057594543583655,-5279219608171455305>()) {
                     case 1257422596:
                        int var5 = -var3;
                        switch ((int)com.yiyiaddon.m.b.a<"s1437akvz3as28","FA51BsdwRoOTQYFHemU/B8gxP1uV6lpTowUlHHiPq98=",2781978786517194376,-1874206297466300649,-8525364597578392843,-2443925866849794160>()) {
                           case -764067021:
                              while (var5 <= var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1vb5cx6w9ij9j","Lwhc4WkFlyhJWNK8HsWOXEnKkChrMelytZtIUD/UZTk=",-85662891951104373,2658610334788527936,6217658236976106977,847950964016901166>()) {
                                    case 348208134:
                                       int var6 = -var3;
                                       switch ((int)com.yiyiaddon.m.b.a<"sp9byacqs5j60","gMbkeTGAWEqCTfuOhvXreYxK3BiyfeeTsB1u4JWynhM=",3611963082736649399,497501117337424970,6296043113712758141,-7921187791242098666>()) {
                                          case -1704012936:
                                             while (var6 <= var3) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sg54mxju4zzyn","/RoKgZnDVuqspnK3Qu1b33laKhjHRuLgXtlvOL12t7Y=",3621414629870874671,-3234544926521125874,1156158037662317439,-2725650506425060423>()) {
                                                   case 345178330:
                                                      BlockPos var7 = var1.offset(var5, var4, var6);
                                                      if (this.ao.level.getBlockState(var7).getBlock() == var2) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s6vppxcqp5gib","vNj25WJ3lWSGUAh+YvqjZSbhRLupSot01xKaeUhjPHs=",-6667359737432142045,1757865830436903870,-9023150002673293186,7001813587363356630>()) {
                                                            case 885157126:
                                                               return var7;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var6++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s22tqvm7wzq9f1","MkByy8URiTNRd22HX1G2jvx4Yu4Oe02lpOZvjMQ+/P0=",1297364718697627387,-7037088870709317095,-4466652052348064265,-2113569462786592568>()) {
                                                         case 483947753:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var5++;
                                             switch ((int)com.yiyiaddon.m.b.a<"sagk065gcpzs6","1QNTf2IDRre3UpFWXScZzqsA5xPHDTGvUtE+B2fLGB4=",-8568407680986829966,2623347554935840486,-5783587844362682669,7418409753135484646>()) {
                                                case 216902461:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var4++;
                              switch ((int)com.yiyiaddon.m.b.a<"s1yrnfihxxsg59","WIJMF0EcM/zKSkgBzZBLmfsnP8qzKXkMfYDRum1IrsM=",6748466627966056613,7571183715978841226,7229387784142271974,3504860793978971147>()) {
                                 case 837397799:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return null;
            default:
               throw null;
         }
      }
   }

   void a(g var1) {
      this.a = var1;
      this.cB = 0;
   }

   void A(String var1) {
      this.zz = var1;
      this.a(g.ERROR);
   }

   void aW(String var1) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sz9jcrqqf8fy1","7R0Vxmai/I2FQiYnwjEZN4tRLyEblZG5NSBZWX2H8V4=",2096632180895770992,-4148520477744643213,-5325791956421917281,1665632598049768526>()) {
            case -2048811144:
               this.a.accept(this.fa() + var1);
               switch ((int)com.yiyiaddon.m.b.a<"s20svyap3g5zj4","ZnilM9jUrEKH0rhuevdhivRMHCLz2oFcCaDcXWYADEE=",-7250550140170344299,4184816078066822039,-4252741086186107790,83231007616063253>()) {
                  case 13589300:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   void c(b var1) {
      this.a = var1;
      this.a(g.CLOSING_MENU);
   }

   int z() {
      return this.cB;
   }

   List<com.yiyiaddon.e.q.f.e> T() {
      return this.targets;
   }

   int cW() {
      return this.qs;
   }

   int cX() {
      return this.qt;
   }

   com.yiyiaddon.e.q.g.a a() {
      return this.a;
   }

   com.yiyiaddon.e.q.e.a a() {
      return this.a;
   }

   com.yiyiaddon.e.q.e.b a() {
      return this.a;
   }

   private String fa() {
      switch (this.a) {
         case LOCAL:
            String var2 = (String)com.yiyiaddon.m.b.a<"s10cztcrycot12","QEs5uSSwzufympYTfgEghWgjFZ05UzSCrFbEPjNuCdB7wyFW",2982845138615499813,8580608471785761129,8963300348744556145,-2667009466529710956>();
            switch ((int)com.yiyiaddon.m.b.a<"s1gxisad53z7xh","ay1de1oCxRzSa9KCP0VIMfLeKnNRnsHZlzYxf7JFvBk=",7063450179432330035,7479448427919705186,7453538835827165235,8125005669472580348>()) {
               case 244035149:
                  return var2;
               default:
                  throw null;
            }
         case SINGLE_PATH:
            String var1 = (String)com.yiyiaddon.m.b.a<"s19r2kfbml7mxl","nR2VJ8XD5OJLDLyDY+fW//qkrbSDZ25DnCd3TnHdBb/fukih",-2388617192787312379,-5918772098196561282,-2893785349316402499,-6393450109174349049>();
            switch ((int)com.yiyiaddon.m.b.a<"s2u00qxmdqcmbc","qb34wuQ/hvEoz+SP90gRYO07LyGBPtJHF/01gg5LNlA=",57991186875490518,-6529270515745514905,-2917396503449591647,-1209112188261893605>()) {
               case 559660668:
                  return var1;
               default:
                  throw null;
            }
         case PIPELINE:
            String var10000 = (String)com.yiyiaddon.m.b.a<"s2tswauxk1qa80","+ub3Cj2KoTzrLIpko5D+/+LPO7x5fdohP8TZahQOMemJuw==",-629443779414145038,1528124294531394723,-428906534317582149,-3076194708628377202>();
            switch ((int)com.yiyiaddon.m.b.a<"s3lf44decfrukj","kFNopsKFrIoKcn7m/dl5RdZiVpH2XQSrk1LKICdnkUQ=",-8436567309999768148,-4145321434305121030,-9044387541426335628,-3607722178648614257>()) {
               case 1893045398:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private String fb() {
      return com.yiyiaddon.e.q.c.a.a(this.a);
   }

   private String fc() {
      if (this.targets != null) {
         switch ((int)com.yiyiaddon.m.b.a<"stqitpr21e0e9","nLuNuMgnC+AmCQaV+h2kf8FVBn5zwX5yPVJmTp+F3mw=",2669439633695260555,-598874163521409493,-1067571084394428275,4253054731075428458>()) {
            case 1002581050:
               if (!this.targets.isEmpty()) {
                  if (this.targets.size() == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1p0p64imjhgnx","RpmoNbnf9t/70x5rj0qmyl/yThaztEcHlPkwfs9xDyo=",-3944028987151552241,-5801769744996187702,7793629993416604364,-6748300708268477712>()) {
                        case 138829264:
                           return this.targets.get(0).fe();
                        default:
                           throw null;
                     }
                  }

                  return this.targets.get(0).fe() + this.targets.size();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3sewz6znngdgp","ekmIuenwhsvpT7ArsIaz3uNUXUFxPGHjO1abqZ8QKbs=",8513366614841410405,-4915330648031825775,6650532471020422272,727709830208897434>()) {
                     case -454908909:
                        return (String)com.yiyiaddon.m.b.a<"s1kdfyozv4zmsz","p2qbgOkg0UI2XBPsdIHHj39YI6gPyzHTL0cMi/8I0bz0iE0n",-7317257748830430256,599573149482001721,8632202131837118157,-1214842090041795961>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s1kdfyozv4zmsz","p2qbgOkg0UI2XBPsdIHHj39YI6gPyzHTL0cMi/8I0bz0iE0n",-7317257748830430256,599573149482001721,8632202131837118157,-1214842090041795961>();
      }
   }

   public g a() {
      return this.a;
   }

   public boolean ab() {
      if (this.a != g.IDLE) {
         switch ((int)com.yiyiaddon.m.b.a<"s18v032v0ndvtg","VW+Xuo35v6iiFYONth0cCIyiOQdFL9by7h+fpPzVZkc=",1752833623581331333,-6621140057552827724,2777921815330473988,2516982018895100112>()) {
            case 1914849435:
               if (this.a != g.DONE) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ufi5pw6utge1","TLnMCjcfUPeuwcrQRtWz4QCfgijTkXX6Qn1K7CuNMIg=",4230037322141764741,-8032499417393178940,-7880097915567624724,-9102656231846349269>()) {
                     case 828153191:
                        if (this.a != g.ERROR) {
                           switch ((int)com.yiyiaddon.m.b.a<"s37lct5e6s9qam","LmdQlIQHm2OF7DEwh+Nhmg3Y6OZ8zrCLwcooBJLaPaI=",5807139978264258908,8265244222647944073,-8749425379142914021,-4702923536417256311>()) {
                              case 1559391482:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1yx0leq8z0b9x","npBjaN3Zt6O7yh1J9BAisD4EYVROXh7uK8fJOtXGrVU=",6516602644548332012,4930617516511737175,-2072622920647584223,9104896749741035089>()) {
                                    case 1016402264:
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s37zwj1e0bdylw","3SogndBUodQOovo15cEBojY3IoGK7JtkJ/r9U6gFMVw=",-4702759355673253632,5481124481290811497,-1786751955838086673,-6729951996937178542>()) {
         case -1264019562:
            return false;
         default:
            throw null;
      }
   }
}
