package com.yiyiaddon.e.j.j;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public final class a {
   private static final String nd = (String)com.yiyiaddon.m.b.a<"s3psdfm4nf23pu","jA3AuVV7i2Bj7FPq8bvfpNHHi+2zSQEoAia/OIXcJGxYl/QISADSjBub0rk79MWy7mDpXYNREwpy5/D7sDs=",154600420132757863,-6287438958219633688,5409898236957898747,6335350315701614238>();
   private final com.yiyiaddon.e.j.a g;
   private final Minecraft M = Minecraft.getInstance();

   public a(com.yiyiaddon.e.j.a var1) {
      this.g = var1;
   }

   public boolean a(com.yiyiaddon.e.j.e.d var1, boolean var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"syq5h296golqz","0Jh7qagjQMKheMjFcr3fvH/+BUjubbyQ7k/UYzUQasA=",3857988572005513612,71228196466439260,8240703202933365854,3234170753448184087>()) {
            case -389443366:
               return false;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.j.i.b var3 = this.g.a();
         if (var3.a(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s32savuvgip66h","vW3ird1EB+3ejFHrvgHIvlTr+lqdQc10zQxOX5q3F7s=",7876103985857877649,-5743694071107335142,2029294212381657821,-4160630020946288880>()) {
               case 492709469:
                  this.ag(var1.m() + "");
                  this.ah(var1.m() + "");
                  return false;
               default:
                  throw null;
            }
         } else if (var1 == com.yiyiaddon.e.j.e.d.AFK) {
            switch ((int)com.yiyiaddon.m.b.a<"s281dsc6onl1cq","cPz4EJ0GcbCjmNcm8c/zTwgEEqybTixC6UOdRkf0qv8=",-7371235576769557146,6295877940375178453,-1518506940751955237,-5315252217706574290>()) {
               case -257510694:
                  boolean var10000 = this.cp();
                  switch ((int)com.yiyiaddon.m.b.a<"s1nvo3hf4t2wyl","miemFNcjcyPn1+Av0XwX+K8+hgnLe+fWMjYy9MZYWLM=",-3117225213029577683,5691227206326841186,8649545077682983948,-7846463237323236977>()) {
                     case -2127058908:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            boolean var4 = this.c(var1, var2);
            switch ((int)com.yiyiaddon.m.b.a<"s2c2kz88rt8lne","WWafAY14cxdzDRE+qrdI4u3z8Upr/MNk82LcczqtE8s=",-3769637041338636356,3732835489561752763,2249723076739087757,1639177666968708303>()) {
               case 48344139:
                  return var4;
               default:
                  throw null;
            }
         }
      }
   }

   public boolean b(com.yiyiaddon.e.j.e.d var1, boolean var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bahl6ov5pwsh","zh3THjIlyXrUZhMAhAG+5Ixg7q51vmZzjyEvZwp/5Bw=",7967986881885337251,5975248765331632541,-8844656542086165077,-209745799452119465>()) {
            case -1097033379:
               return false;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.j.i.b var3 = this.g.a();
         com.yiyiaddon.e.j.e.c var4 = var3.a(var1);
         if (var4 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"stfdje7rkwrk6","iqxidc/SSIK3/BTCxN0We803tjj8O8p+vpQ2viTKXU8=",-8991660952404835204,3060971717581232060,5881647189918255710,-6225579459048851150>()) {
               case 985364870:
                  this.ag(
                     (String)com.yiyiaddon.m.b.a<"s264wcukcyrjwe","3ZEvLowRpozojZ/hKt52lfK30nrXEXwjuUSpmlGzEXp2zzbXZTtXiX45KqlKto6x",2480428589507624726,6619047485988363712,-185898963527063762,8694789983112586860>()
                  );
                  return false;
               default:
                  throw null;
            }
         } else {
            var3.b(var1);
            if (var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s12gak20ldhqgz","fUifavhOE2sBtbaQEv4VVIAyaBcoI8oxWxCRW90taWE=",-7571614644258953430,-3792922108704242432,5683905853714562785,2881991592332817939>()) {
                  case 1456409498:
                     this.ah(var1.m() + "");
                     return true;
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"s1t5sh3us6ldvl","YbvQDHVP6mGMHhM5KvB8pO2/dHBc2KYOsZNKr6WZ3DaGwa4+",8028183268071987485,6823550002443501591,-8724147301449274459,-4539567625717241634>(),
                     var1.m() + ""
                  )
                  .a()
                  .a(var4.bU())
                  .a(var4.aj(), var4.ak(), var4.al())
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s1yp1612oja9x0","L36mFTcw4TKCEPmlZwzTceEHDxWgPiwlA5cwgRVIolA=",-5138523180305501531,-2707724418603499335,-5429428982408286671,2882306869677444411>(),
                     var1.m()
                  )
                  .a(
                     com.yiyiaddon.d.d.a.SUCCESS,
                     (String)com.yiyiaddon.m.b.a<"s2qmv7xeyf750q","JftTSz0UtXfjSeGc4i3FyaEvFbUVzICS7F2XBWOr5QcExg==",6732105149529773030,-3263424006112576168,-4477564185418504820,1045735929701695828>()
                  )
                  .g();
               return true;
            }
         }
      }
   }

   private boolean c(com.yiyiaddon.e.j.e.d var1, boolean var2) {
      BlockPos var3 = this.b();
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sgtvt64yzbnnk","0NCqjYh9ZnU/S61MuT6Jy54pedXDI+5BYM69ALbSKJw=",-819508580982707226,4457362292674711961,7876150127874303564,2922401530897569844>()) {
            case 2023069265:
               String var5;
               if (var2) {
                  label34:
                  switch ((int)com.yiyiaddon.m.b.a<"s1r3skv20wnhub","FL8edWFeAdLJGvdxpsg87F1Xf/XSVDZbrDJ+nwvTlro=",-4784007826971574301,3682347062013491382,-1281256003944660421,5815383319867414941>()) {
                     case -381389389:
                        var5 = (String)com.yiyiaddon.m.b.a<"s3muhqyrh68qaj","n1Mb8ClAfrwdBCRUjl8oKb4eswQw3xei23cjxrMt0m42tqP4JF2XN8zXpMeShUHM7rSVvQ0XVKF+aA==",-313011274297292703,979587420040603210,7739906323375060247,5999340620542475023>();
                        switch ((int)com.yiyiaddon.m.b.a<"s2bm0duufcslr5","qRZuTDV7tFu8gURFtYP3cv7rBr1GmAJj/UGnaNsROlo=",7170157874473079252,-3198425793108485959,4191272606667310434,1669039687603482693>()) {
                           case 597900702:
                              break label34;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var5 = (String)com.yiyiaddon.m.b.a<"s26cx3iyxvdy85","PPd88Yb6LYIzadWnQMcs+MH6VNmqtYLdVz1ReOiMtRfW7gRRlq6MZz80vhYLaQ==",3786737694840484371,-8307032705026962786,-5347123215218218922,8349770892085154756>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1cvywi3ijbam0","eIwXts1YaWLNhbvUysxFGnlsgZfn/bCkNZNTqXQ6/+Y=",-1409824902315311859,5617273711776584637,3719819690803768219,-3360779388774367296>()) {
                     case -983606116:
                        break;
                     default:
                        throw null;
                  }
               }

               this.ag(var5);
               return false;
            default:
               throw null;
         }
      } else if (!this.j(var3)) {
         switch ((int)com.yiyiaddon.m.b.a<"s198jdoh4g87u2","/P1+F8AzM9BkH80pvwdo3zpDGA2qUZBzSsjQsBf+dHM=",5855743600524118159,-2718741073270668556,3967480265406046855,8161404021509113977>()) {
            case -870045096:
               String var10001;
               if (var2) {
                  label42:
                  switch ((int)com.yiyiaddon.m.b.a<"s1efecoogyr6ul","CXOldY/7JvK1NLehHpixL+tNSG2ATQwU1/krZs8HDJI=",-3568195800430585765,-2340206771537281065,6672514342320680556,5321458760778479743>()) {
                     case 1723280046:
                        var10001 = (String)com.yiyiaddon.m.b.a<"s3kmjt5o48fkcz","/WtWMCdaaDi9ctpLjL+QvzVCrpwecHBhMtiWpS7VofwqLEObzHJFxdnQ0dFeTjCHOZa7U0+7ESu/g/BsY3O6DLz28cFtO/KC7Z0SjCIn",-5424569553142664456,-6973059895290699380,-6921150704693374764,-5705485715979005551>();
                        switch ((int)com.yiyiaddon.m.b.a<"sw3gjuobinamv","/iKrKSUsSYiSnzeZDVxSHtj9qa/KOPROaKIOpsUYxEk=",-2898253885091120882,147976700244825203,-4370730787672518322,8408539468101347683>()) {
                           case -202019718:
                              break label42;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = (String)com.yiyiaddon.m.b.a<"s2tmlmt8mqcy99","KdBxDlVraBEchCZWXCCkCG7CSBOThhxNf9mmtntPGS/jKvYHXDdlPvjYJDQ1vQMrzHsp+Dqu8WBVSsfS3+p4uldQ",6152340846349282396,-992043684771437806,-1828035771122809734,-5794687650964800742>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3pdpwsnrmg1zi","1wadUOZV7WGvMhPLQX2Fvpdk+2Y7Sqi0d+bTfdIzgKo=",7680238882750675707,5354977346524073647,5105496900726553315,8468514647651328758>()) {
                     case 898258202:
                        break;
                     default:
                        throw null;
                  }
               }

               this.ag(var10001);
               return false;
            default:
               throw null;
         }
      } else {
         String var4 = com.yiyiaddon.i.g.c.bU();
         if (var4.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"slcozf6jkqp4g","B9aftbFO+l/4v9/o6EhEpjD2nIZKFAdwSNXOOOvHK24=",-1211057985218415510,-7225729030866897660,4577690268235568050,4526180039346994070>()) {
               case 607225676:
                  this.ag(
                     (String)com.yiyiaddon.m.b.a<"s7bf0v2jll93y","YbR9zaUEDiJjohNT0GzStG7zRnbDa2FZ0mcM/K7ECpdA1k9LBw2b7V63oStEbD0R",-1741415910736444912,-5389899601941802954,3236080518866011865,262280583732854011>()
                  );
                  return false;
               default:
                  throw null;
            }
         } else {
            this.g.a().a(var1, new com.yiyiaddon.e.j.e.c(var3.getX(), var3.getY(), var3.getZ(), var4, 0.0F, 0.0F));
            this.a(var1.m(), var3.getX(), var3.getY(), var3.getZ(), var4);
            return true;
         }
      }
   }

   private boolean cp() {
      if (this.M.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1i585hcxpymaf","xvEwA894DAlnFaqE1JPt9m+vcL+M7DcxzCwIvrc8t3M=",-2557129677843351800,2269439107217049201,4826451244166420118,-5363142615181684186>()) {
            case 253100265:
               this.ag(
                  (String)com.yiyiaddon.m.b.a<"s2bkkxiz3wl07h","6O6KDe4HVJBPzgrH9QE5DRmLAHp5nRBHpwSdwC57/zs5xfhfftw=",-4564676122771057953,8336819434746122533,-3107221828684810726,-6153637254839121636>()
               );
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.M.player.blockPosition();
         float var2 = this.M.player.getYRot();
         float var3 = this.M.player.getXRot();
         String var4 = com.yiyiaddon.i.g.c.bU();
         if (var4.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"stp0k9yx3f4kn","SgRUqW6a5BQz7EG3kUHo3jjfp6cs8DfF11+8dd2LRa8=",5668409371232096814,-1914803133695340218,2072723168241738478,-2901548580808888613>()) {
               case -1849363060:
                  this.ag(
                     (String)com.yiyiaddon.m.b.a<"s7bf0v2jll93y","YbR9zaUEDiJjohNT0GzStG7zRnbDa2FZ0mcM/K7ECpdA1k9LBw2b7V63oStEbD0R",-1741415910736444912,-5389899601941802954,3236080518866011865,262280583732854011>()
                  );
                  return false;
               default:
                  throw null;
            }
         } else {
            this.g.a().a(com.yiyiaddon.e.j.e.d.AFK, new com.yiyiaddon.e.j.e.c(var1.getX(), var1.getY(), var1.getZ(), var4, var2, var3));
            com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"s1t5sh3us6ldvl","YbvQDHVP6mGMHhM5KvB8pO2/dHBc2KYOsZNKr6WZ3DaGwa4+",8028183268071987485,6823550002443501591,-8724147301449274459,-4539567625717241634>(),
                  (String)com.yiyiaddon.m.b.a<"s3hmt6y400rceu","vK6CKeUWOlLOsMnSdJ7aa/XEYquvyCs5ZPCAJE9IoVk+nWtoTGx0YK5iSS0=",-6986141439409771546,1968239869895152645,1893945499804541320,7613121514104973787>()
               )
               .a()
               .a(var4)
               .a(var1.getX(), var1.getY(), var1.getZ())
               .b(
                  (String)com.yiyiaddon.m.b.a<"s1yp1612oja9x0","L36mFTcw4TKCEPmlZwzTceEHDxWgPiwlA5cwgRVIolA=",-5138523180305501531,-2707724418603499335,-5429428982408286671,2882306869677444411>(),
                  com.yiyiaddon.e.j.e.d.AFK.m()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s2heih6lfopokf","NRnKTfKMyULfzmFHcFxgCwdXQdjwp7ee5bNurF+7PLM=",-2711937754252404727,-135180742039085563,4783354764176765798,-1439700704084109124>(),
                  String.format(
                     (String)com.yiyiaddon.m.b.a<"s2sg57u2gbpfx","xpdEROjBr1RH+i8krqKUhjOW6LrjIiPlhEI3jbKxag8aodCnlNMgzfZUubxgHGgxbgiKlmXUGs/x0eH3FCmmSm1NOpOXWA==",-6716560003611326323,4709248585625520515,-5011872673052564205,9024572625983817862>(),
                     var2,
                     var3
                  )
               )
               .a(
                  com.yiyiaddon.d.d.a.SUCCESS,
                  (String)com.yiyiaddon.m.b.a<"s3pztqmh72zo5n","DRYZwf7wIoAXbJl98dLWtEkzvhnceS7uGWctWGfmlHv0lg==",2099569747361007613,8244882595810189084,833069140609707441,5254532542232683823>()
               )
               .g();
            return true;
         }
      }
   }

   private void a(String var1, int var2, int var3, int var4, String var5) {
      com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"s1t5sh3us6ldvl","YbvQDHVP6mGMHhM5KvB8pO2/dHBc2KYOsZNKr6WZ3DaGwa4+",8028183268071987485,6823550002443501591,-8724147301449274459,-4539567625717241634>(),
            var1 + ""
         )
         .a()
         .a(var5)
         .a(var2, var3, var4)
         .b(
            (String)com.yiyiaddon.m.b.a<"s1yp1612oja9x0","L36mFTcw4TKCEPmlZwzTceEHDxWgPiwlA5cwgRVIolA=",-5138523180305501531,-2707724418603499335,-5429428982408286671,2882306869677444411>(),
            var1
         )
         .a(
            com.yiyiaddon.d.d.a.SUCCESS,
            (String)com.yiyiaddon.m.b.a<"s3pztqmh72zo5n","DRYZwf7wIoAXbJl98dLWtEkzvhnceS7uGWctWGfmlHv0lg==",2099569747361007613,8244882595810189084,833069140609707441,5254532542232683823>()
         )
         .g();
   }

   private BlockPos b() {
      if (this.M.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2y4p4lfxbahle","eXe2vJUZw432DbTYF1oJH4fgdAqJr6Zl8rddR1P9egY=",-1712203293911010620,2100746738181944772,-312370857478466651,-8339400207743484102>()) {
            case 676207326:
               if (this.M.level != null) {
                  HitResult var1 = this.M.hitResult;
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s20olkv3mrfcii","PKxEkR2H8zFmm2A/UpfML2FxcXja22Rb70b/XcXabhQ=",105040422990959717,-3769840612855622872,1297348799963260327,869715068452661871>()) {
                        case -133085370:
                           if (var1.getType() == Type.BLOCK) {
                              if (var1 instanceof BlockHitResult) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s8380mlbpspee","Ca9M/XDhlQPqFnFV8nWHsvA8MVqe7AZLgILziz9F5nk=",8745562461363437753,4753926068403481800,-8339762927294274889,7461623235939605656>()) {
                                    case -108608085:
                                       BlockHitResult var2 = (BlockHitResult)var1;
                                       switch ((int)com.yiyiaddon.m.b.a<"sko6vg5rvaar8","rChVZblBVIkoRBPxuzIPxHOwn1QBYjfBz5AwmEQdnPM=",-5200922856982821750,5948402807812179803,5312454638650026757,6603405512226686746>()) {
                                          case 338195538:
                                             return var2.getBlockPos().immutable();
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return null;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s5ivx2dga65sf","14+S0lOwFYPvsiYGY5e7HWUDF+mNL66Mt5/aP33q4go=",7060695615946859642,3766114180924028833,-8505487357429411061,2217155662807120731>()) {
                              case -1739500545:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sjyopelas9tr4","3ByOZjZNTIh1RG6HOOvzDs9p+yudBwYxAeit3yx4Zow=",115340375756450416,-8987050368429161101,2798635214185440946,1711252699913980851>()) {
                     case 789985242:
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

   private boolean j(BlockPos var1) {
      if (this.M.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2yi4hut3paek4","lU9ArrnqmzAmN6yAhxwsFgdd0azW6zmDQKXtiiKhwU4=",-8681610886563228084,-1901935781386935483,753764994199969813,8775605228557964098>()) {
            case -388708412:
               return false;
            default:
               throw null;
         }
      } else {
         BlockEntity var2 = this.M.level.getBlockEntity(var1);
         return var2 instanceof Container;
      }
   }

   private void ag(String var1) {
      com.yiyiaddon.d.d.b(
         (String)com.yiyiaddon.m.b.a<"s1t5sh3us6ldvl","YbvQDHVP6mGMHhM5KvB8pO2/dHBc2KYOsZNKr6WZ3DaGwa4+",8028183268071987485,6823550002443501591,-8724147301449274459,-4539567625717241634>(),
         var1 + ""
      );
   }

   private void ah(String var1) {
      com.yiyiaddon.d.d.b(
         (String)com.yiyiaddon.m.b.a<"s1t5sh3us6ldvl","YbvQDHVP6mGMHhM5KvB8pO2/dHBc2KYOsZNKr6WZ3DaGwa4+",8028183268071987485,6823550002443501591,-8724147301449274459,-4539567625717241634>(),
         var1
      );
   }
}
