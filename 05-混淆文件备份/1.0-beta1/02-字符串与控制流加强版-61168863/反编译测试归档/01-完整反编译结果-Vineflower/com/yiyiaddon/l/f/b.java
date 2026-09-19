package com.yiyiaddon.l.f;

import io.github.humbleui.skija.Canvas;

public abstract class b extends a {
   private static final float iS = 0.16F;
   private static final float iT = 1.0F;
   private static final float iU = 96.0F;
   private static final float iV = 20.0F;
   private com.yiyiaddon.l.a.d[] a;
   private com.yiyiaddon.l.a.b[] a;
   private int tx;
   private float iW;
   private float iX;
   private float iY;
   private float iZ = Float.NaN;
   private float ja = Float.NaN;

   protected b(int var1) {
      this.O(var1);
   }

   protected final void O(int var1) {
      int var2 = Math.max(0, var1);
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nvtxzfvriypg","45J+Ek7ZSJgXSukHD2bvO49SIhVDjUrOe2EbovACS4k=",6184270480901152695,-871414779169083123,907462365858076418,-5819534062879923027>()) {
            case 1973174899:
               if (var2 == this.tx) {
                  switch ((int)com.yiyiaddon.m.b.a<"shsvbcfscgsc2","zGxaseAhZrbulA4HVCowW+maEAENz11I0wL/pNoa/K4=",-1147577071543261063,1137519235328199750,1131060572790233789,-6755723069945985565>()) {
                     case -717384526:
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

      int var10000;
      if (this.a == null) {
         label55:
         switch ((int)com.yiyiaddon.m.b.a<"s2av0tr1q962xj","Ob3Yi3DwhXsv1Ek5lXGYo1FuzBOTahzf7Upv3oc7CVc=",5089069419032119299,-4186282182618613002,-6969249528972465937,-2675974713094477136>()) {
            case -1314809608:
               var10000 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"skssw1by4sgef","vYNsxWlCo3bSmH96YGQiZawAkziUpgGBB4t9RWscpBs=",-7058698559380259096,5119096891028852164,-9055678614886954512,6482327058682451122>()) {
                  case 1443478796:
                     break label55;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = Math.min(this.a.length, var2);
         switch ((int)com.yiyiaddon.m.b.a<"s1y9obkcuymr3c","47CYbokoe0Wf+RJh4MQNPnEW0rCjKHlu0siDRuQhVQQ=",-1323370824782056348,657691506435493813,4860146568607122903,-1054892729195814833>()) {
            case 598049414:
               break;
            default:
               throw null;
         }
      }

      int var3 = var10000;
      com.yiyiaddon.l.a.d[] var4 = new com.yiyiaddon.l.a.d[var2];
      com.yiyiaddon.l.a.b[] var5 = new com.yiyiaddon.l.a.b[var2];
      int var6 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"shexblecbaovb","FW9o7OHsquQU7CWBlPJHTuKC7y+3bnRCERuZwdGSKaU=",456070600950009896,8093454838751805878,1262904570449364,5482613887373219088>()) {
         case 199436109:
            while (var6 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"snwzqhqf463ar","KypUCkKJiYpS/uE77IpXViYviDLChpe1jTgWFVwcupo=",5533512617508091628,-679741634627387505,-1479891568733854843,5202746676456761053>()) {
                  case -1460531797:
                     if (var6 < var3) {
                        label42:
                        switch ((int)com.yiyiaddon.m.b.a<"s1spmbhobq6tfe","fLnueX3xaeSW/89B1I4KuvWDF4b9WfcKOEEzl5J2oT8=",-7779379051072560318,-591541094607812900,-6988955230927663305,-6861655545977290350>()) {
                           case -1109683731:
                              var4[var6] = this.a[var6];
                              var5[var6] = this.a[var6];
                              switch ((int)com.yiyiaddon.m.b.a<"shf8cmdc0dd61","BW7QgchWL+ScR4kiUUnVuA4rr2HKK8n6uHDEIQM5dZ0=",7367809875810731562,843996951465782855,9175000657068431746,4977441339324811060>()) {
                                 case 1290167885:
                                    break label42;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var4[var6] = com.yiyiaddon.l.a.d.a(0.16F);
                        var5[var6] = new com.yiyiaddon.l.a.b();
                        switch ((int)com.yiyiaddon.m.b.a<"s3c1qt96rjpsuy","kKJ8yVpsZB8C0sBhA6pWcmNpVUMQSP/CrAqOO170K+A=",-9173199283844586622,-2409725293422676710,-1482605411140948113,-4180850388046494865>()) {
                           case -208440376:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var6++;
                     switch ((int)com.yiyiaddon.m.b.a<"shkjhxasmat6l","CffrlpmtKQ25BaIBV2GLYlKl2xSAnqwW0Ck6TkLBSk8=",-433343751121429692,-3024311199958549593,-7835279588355234074,-7548670461095246880>()) {
                        case -984256173:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.tx = var2;
            this.a = var4;
            this.a = var5;
            return;
         default:
            throw null;
      }
   }

   protected int dY() {
      return this.tx;
   }

   protected int dZ() {
      return 1;
   }

   protected abstract float w();

   protected float a(float var1, int var2) {
      return com.yiyiaddon.l.b.d.a(var1, this.dZ());
   }

   protected float a(float var1, float var2, int var3) {
      return com.yiyiaddon.l.b.d.a(var1, var2, this.dZ(), var3);
   }

   protected float b(float var1, float var2, int var3) {
      return com.yiyiaddon.l.b.d.a(var1, this.w(), this.F(), this.dZ(), var3);
   }

   protected float n(float var1) {
      return com.yiyiaddon.l.b.d.a(this.tx, this.w(), this.F(), this.dZ());
   }

   protected int a(float var1, float var2, float var3, float var4, float var5) {
      return com.yiyiaddon.l.b.d.a(var1, var2, var3, var4, var5, this.w(), this.F(), this.dZ(), this.tx);
   }

   protected float F() {
      return 14.0F;
   }

   protected abstract void a(Canvas var1, int var2, float var3, float var4, float var5, float var6, float var7, com.yiyiaddon.l.i.c var8);

   protected abstract void P(int var1);

   protected String gE() {
      return (String)com.yiyiaddon.m.b.a<"s1l4sa0sluyr6y","cqsEZc6QRexpYBlnWp6zQv75/d9UrcqPLkTIjQk8VAj5Ev/y",-1693988457500605556,3323670588430442556,5381177647632611471,5643866116188280556>();
   }

   @Override
   public float D() {
      if (this.tx == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sevcaxs6x6zpn","74WIyTqUX+ldAveF8R2mGNCvXiAA8NgGxU10CWeNMds=",-2181620727192532759,-380862289303026079,-2803278130805841423,-2033470580835899101>()) {
            case -1647000397:
               return 116.0F;
            default:
               throw null;
         }
      } else {
         return 20.0F + this.n(this.iY);
      }
   }

   @Override
   public void a(float var1) {
      int var10000;
      if (Float.isNaN(this.iZ)) {
         label47:
         switch ((int)com.yiyiaddon.m.b.a<"s3iok7ve75m9o","3Hp/dVFOOpo+91K4SORqQryR5H3qtmOOcV4jFClBwK0=",-6411616495139974967,-205393623749074317,35602119061261741,7595400410109474323>()) {
            case -937860190:
               var10000 = -1;
               switch ((int)com.yiyiaddon.m.b.a<"s4s81pxjsh228","LpqkI9l+1lfUjUoPF3lYSsh9s7pUDJR/OpUrmcq7O2A=",9024104146148838150,-1342962799009752864,-5243760677385053328,-5331469347286462078>()) {
                  case 1434789606:
                     break label47;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.a(this.iZ, this.ja, this.iW, this.iX, this.iY);
         switch ((int)com.yiyiaddon.m.b.a<"s1c7fog4plb63x","Ky2eOi8n0LBD9QqR267xz/YD+J8TR+OkNFkxYi6dGxQ=",-7235433240695073264,-7598294876552040430,-5994651741741668032,-6595563812923605840>()) {
            case 1625099217:
               break;
            default:
               throw null;
         }
      }

      int var2 = var10000;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1cvx2r36wvjqt","2bbkR2lDH385BcDoZaUI17fFzum3vwXR5/LKHK/5kH8=",1205665720916384106,-2433691300051415873,460973211050185436,-8366108378126265464>()) {
         case -1264585891:
            while (var3 < this.tx) {
               switch ((int)com.yiyiaddon.m.b.a<"s34ejcwurbr9zz","iwvpQlhIZ8P5ZsaZDS2pnjYqf58qIPOk6+kkWrp8IB8=",-7006319966219491022,-4098989952840607634,-6558459456564750073,-8202240136820190654>()) {
                  case 411523145:
                     com.yiyiaddon.l.a.d var4 = this.a[var3];
                     float var10001;
                     if (var3 == var2) {
                        label34:
                        switch ((int)com.yiyiaddon.m.b.a<"s1eityf8otiney","y/d+BsC5Pz5nDW63KegcT0E2Ve+bgxAZbbP4lX98HpM=",-2848191969819279074,-2875979901892740585,2498239341651259271,7211085447016199144>()) {
                           case 1895843653:
                              var10001 = 1.0F;
                              switch ((int)com.yiyiaddon.m.b.a<"s2osmv0cnnn8i2","dOCoHjsviQozPmGQ2H8Ey4IPfOu1En4p9jORyoZNNIc=",-326388188624396904,-3811530777800032329,4818392522402712751,-5230129838063373284>()) {
                                 case -2024244252:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = 0.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"sauqut2mcinhc","PhbapZ3KhvgPEy6qICu8QoFV8z2kjWW6sCRfb6JiMm4=",1639255772344135467,-3327270735863014726,-8822510916616779997,1710777661512128318>()) {
                           case 548238592:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var4.d(var10001);
                     this.a[var3].a(var1);
                     this.a[var3].a(var1);
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s17bi4sdug1k84","sdGLdofNFf1wly8ya6XdxQTMEXOzSLULSujffqVk2Pg=",5599336164674592057,-1353233203833158487,-4112902392131752066,-8299169378451111348>()) {
                        case 138322807:
                           continue;
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
   }

   protected final float G() {
      return this.iZ;
   }

   protected final float H() {
      return this.ja;
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      this.iW = var2;
      this.iX = var3 + 20.0F - var7;
      this.iY = var4;
      this.iZ = var8;
      this.ja = var9;
      if (this.tx == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2urrnw4i3o7p7","TmUTCoCQZ/LvYVmx/zYLXiMdMvRNafVjQ1qzxhkXRVc=",-5785394943128135602,-6491129052563988311,4781067758207739353,-2279050753740471932>()) {
            case -664783403:
               this.a(var1, var2, var3 + 20.0F - var7, var4, var6);
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.l.i.c var10 = com.yiyiaddon.l.i.c.a();
         float var11 = this.w();
         int var12 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1dnrmmhytdono","zVjVENOFbV1rua6d5KRlryd+c0KiylmzXGQpJWL86/M=",8222548233736761021,-8827868386755265795,-6272221910647355266,6523008413960961145>()) {
            case 594098736:
               while (var12 < this.tx) {
                  switch ((int)com.yiyiaddon.m.b.a<"s38g6wsiem4ke8","4BeKcAqvCLcrmR75yQC2LtGKRH1i+tRvR3ljWdAinmE=",-5789294200407791130,-4885296568447161120,-6561848412933381724,7553892561039554224>()) {
                     case 1851351602:
                        float var13 = this.a[var12].r();
                        float var14 = this.a(var4, var12);
                        float var15 = this.a(var2, var4, var12);
                        float var16 = this.b(this.iX, var4, var12) - var13 * 1.0F;
                        float var17 = this.a[var12].p();
                        boolean var10000;
                        if (Math.abs(var17 - 1.0F) > 5.0E-4F) {
                           label50:
                           switch ((int)com.yiyiaddon.m.b.a<"s15t2ix0qvodee","CeQ6x69GBgoDnlcZHj5XymWN9iL8v0C9O6oIZBuX+8w=",2452026898841314802,-3553519743647382375,6879260449776842237,-6015646494727013163>()) {
                              case 2044865614:
                                 var10000 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"sbll3seu3md7s","PZOfr4f78IFhyhtwQD2vFlY2xkgY3p1RKRTxYqXcHe8=",6388990695499060592,2081693268565651995,7580692913967473452,1143320461866188226>()) {
                                    case -1508120077:
                                       break label50;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s1f1rqs9vm2lla","jFMUg5qaoLjYVJ35PNnRWvq/PMv1RcDPc3tkgN7ubTs=",1785864577488567939,4551073385874884448,4977313644284390975,-3026950407367201969>()) {
                              case -1161940450:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        boolean var18 = var10000;
                        if (var18) {
                           label46:
                           switch ((int)com.yiyiaddon.m.b.a<"s2znmwatt65h1n","FZiuTsRgxfL9uaCKll/55FmvKPDjcTxHp5Q04dYi4nM=",-6755008412429768572,-2976555669389357156,976056819774898974,-3170221633490603623>()) {
                              case -1582270768:
                                 float var19 = var15 + var14 / 2.0F;
                                 float var20 = var16 + var11 / 2.0F;
                                 var1.save();
                                 var1.translate(var19, var20);
                                 var1.scale(var17, var17);
                                 var1.translate(-var19, -var20);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2e0sj1c1wjpq1","Sw4v5HL3t/p8QKAsrDaWQaR6KHPnBv1Pu+MjtamSY+s=",-6702892576118475195,2352839762677965956,-4457680722545355232,1238957516672087527>()) {
                                    case 289489003:
                                       break label46;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.a(var1, var12, var15, var16, var14, var6, var13, var10);
                        if (var18) {
                           label42:
                           switch ((int)com.yiyiaddon.m.b.a<"s11nx4a4t4btyi","ZulULYKugd4aLP2XS1btgT1WuDctsvyKXpD0xErKkwU=",4413463702209699570,-6295654602944830711,-2292699344531703745,-4827692464587421560>()) {
                              case -1325831028:
                                 var1.restore();
                                 switch ((int)com.yiyiaddon.m.b.a<"s2cz34jbl9zd6g","+3nq6HB2K3Gs4pWy/2vreu6Lw+BCefGScfgxum2MRjQ=",4141684099845029413,-8706998808372085552,-2223511571968862477,-745968575305060683>()) {
                                    case 1497062212:
                                       break label42;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var12++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2ie491lq9huxj","N730JHK6FezsQwsVbjVyAwyVkwzlFcOR1QT0twNP/g0=",-4066687527590906676,-7782742788241526110,-3658214762634587310,-894563363074236389>()) {
                           case -620501401:
                              continue;
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
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      if (var7 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q9cpc5xvgwa6","J4DkhBMYKkg25XmvN37DXXr1MBdfb66UQoeYwfeUnrw=",-6795851035789440120,7818425552517388206,6817212868251997303,1131875195683238316>()) {
            case -329974717:
               if (this.tx != 0) {
                  int var8 = this.a(var1, var2, var3, var4 + 20.0F - var6, var5);
                  if (var8 < 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3r7upo61r32pz","rMZeM985F/UduMd8CT45KYkf3yUi26AV3R48BirYZH0=",2980350800281158581,-4434153712645203902,5134642728353019273,-6653515921294799667>()) {
                        case 829286990:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  this.a[var8].jJ();
                  this.P(var8);
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dp20vjsqk9ha","1HzVCYtLM1TiPUy5c7OMR1eS7JyJY5bLv5q65WvyogI=",8537070889334090275,4955757215137498426,-8368867561712912620,8196234268762532311>()) {
                     case -1995303659:
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

   @Override
   public void jU() {
      com.yiyiaddon.l.a.b[] var1 = this.a;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1y2wf8et1j9a5","Df+s3SVgUlAD7ddjV9N9lU9+Lpr4gFCU1rrVcO4sBWY=",1286446932077618203,6555077846571734092,8357736591098479436,2258848218268692538>()) {
         case -790337370:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s1bdhlahyjmw0p","ZIJmkl6Fp6+jG7MuOgzS7WWc3fjl4eYjn8CFTT11S2U=",-3630033045182262764,3242780076534977854,-7567368183975361499,3064154790727326846>()) {
                  case 1601703614:
                     com.yiyiaddon.l.a.b var4 = var1[var3];
                     var4.jK();
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s38i661q8q36vg","vHtTqkZ4r2kPZI0C20P2D+IpWXoZ0rW7o7VQXs9A7sU=",1149595223792314120,-6955487489177503484,1868718557822590173,8773132033555634657>()) {
                        case -1379607934:
                           continue;
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
   }

   @Override
   public void jV() {
      com.yiyiaddon.l.a.b[] var1 = this.a;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1uldduwdmvy3l","9AllgrgSPJMexK8eEFsSuOdC/epWGPgWL93KcJAw42c=",-2225810209042545730,6280759528160916470,-4016618434079773088,1563362187026155392>()) {
         case 2127004397:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s3c820ibi6f5xq","7BxvPa4ouOfDH09PxfUcKCqjzOxRSj6FWqsd6FNPRUc=",5251213547780096661,3227251518201544539,1384563491117155406,-2871439573781922976>()) {
                  case 14137456:
                     com.yiyiaddon.l.a.b var4 = var1[var3];
                     var4.i();
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3k1haitbtbjiy","wA2I4ioNIUP+rgMit1ulc+cgImWhFR5n8FBLLhhazR4=",-6015772605128723176,2443501091654380196,-2695602896914375642,4977431483554131002>()) {
                        case -1692342056:
                           continue;
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
   }

   private void a(Canvas var1, float var2, float var3, float var4, float var5) {
      com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
      float var7 = com.yiyiaddon.l.i.d.a().a().an();
      com.yiyiaddon.l.b.j.d(var1, var2, var3, var4, 96.0F, var7, var6.uY, var5, 0.45F);
      com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 96.0F, var7, var6.uQ, 0.7F, var5);
      com.yiyiaddon.l.b.j.c(var1, var2, var3, var4, 96.0F, var7, var6.uX, var5, 0.1F);
      String var8 = this.gE();
      float var9 = com.yiyiaddon.l.g.a.b(var8, 11.0F);
      com.yiyiaddon.l.g.a.b(var1, var8, var2 + (var4 - var9) / 2.0F, com.yiyiaddon.l.b.d.c(var3 + 48.0F, 11.0F), 11.0F, com.yiyiaddon.l.b.j.a(var6.va, var5));
   }
}
