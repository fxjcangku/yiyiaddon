package com.yiyiaddon.i.a;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;

public final class c {
   private static final int sC = 2;

   private c() {
   }

   public static MerchantMenu a() {
      LocalPlayer var0 = Minecraft.getInstance().player;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f40rueg8hifo","hCX/TUMNnn2srs4jp26VZXd8emz1PCwhFArxZWOM0SE=",-8538361180483856880,4858133721713091807,7866390833175472542,4659478655251287270>()) {
            case 1513827270:
               return null;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var2 = var0.containerMenu;
         if (var2 instanceof MerchantMenu) {
            switch ((int)com.yiyiaddon.m.b.a<"s8dqswte586j","3hbmic1tGBoTmyPqiUnA35mHuQBuIDc/SI1gkZhxtmg=",3283402075078351783,2014855964427600463,-1456474791302554868,-8010520390609582747>()) {
               case -826934043:
                  MerchantMenu var1 = (MerchantMenu)var2;
                  switch ((int)com.yiyiaddon.m.b.a<"sgalhk4vuqfua","0SmhuvMH7VEgd+ECnCJ7+aQRYzB8bttCzMBmwjMl6Wg=",-4635112703055865808,8057101720015973948,8207054713687177860,-3283455090623130102>()) {
                     case 888514260:
                        return var1;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3hq3t0klxa465","Kc/bGM/lT2KfXpmp0rtjWUGZMuetcCx/b1QO74bTxYQ=",5517888815026316417,7620250155863101140,-531845793610789826,7355442999233183270>()) {
               case -210500724:
                  return null;
               default:
                  throw null;
            }
         }
      }
   }

   public static boolean fr() {
      if (a() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vjezxn21tltm","iDDH5XGqyNa9ZJH9X0rLwGxFn/ejVE2rUyGAqdM7Wwg=",6726950409371294663,-8093426878636325055,1873734118690857854,-5221329945918247092>()) {
            case -1976831356:
               switch ((int)com.yiyiaddon.m.b.a<"ssftufcxhoa7f","tIcedAVNUzwllRssXOjENsewxhJLDHd/MNv1Dny+JJA=",6365412516699943453,-1573659985651171946,5211566326915850849,2845587289611781409>()) {
                  case 409971121:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2ij63hqlkkvyx","0RQ2/3X+rsRk6r+0NQahtGC6LEh23h60/nQLysBpRUQ=",-2800034864768157769,3983737209938221766,8791464445784479227,-8950106651524257456>()) {
            case 743926131:
               return false;
            default:
               throw null;
         }
      }
   }

   public static void M(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sinmgpp125wif","rse3nmxzadlRaH94667S/Bz4eQEJpLlRtyEYEqpkJUE=",2272605118359007200,8644144403162885790,6336646248289925461,2038203509683068433>()) {
            case -1005673652:
               if (var1.getConnection() != null) {
                  var1.getConnection().send(new ServerboundSelectTradePacket(var0));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3rcsc08omjbzv","539yTqffEYFINF6u47DtdaHwV+WJq7MTxD0f+A5drc4=",-2520631432870188466,2005543136538702557,-5343310232583948864,4591367758364248816>()) {
                     case -1237227498:
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

   public static boolean fs() {
      MerchantMenu var0 = a();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1po23yjw5z055","Q8DSf2743TaKq9rOKBQxQLVW3eXUww5dy9N5xPm9GM0=",4713500637515782722,5711720804460251857,3112963913170575427,4262121144269881985>()) {
            case -1215265816:
               if (!var0.getSlot(2).getItem().isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2wel6r3cy4mz6","9JopU18CP2g0nIKV1dpO5WGlfXfWWArfarInpNr033o=",4707587669755671510,-1775998758162465838,-2540907138881979565,-5698615990887738832>()) {
                     case 1082440701:
                        switch ((int)com.yiyiaddon.m.b.a<"s2o5vk2bao11oi","4LmQoQt7NXYMCIwD3a5rd4a9470jY1AcP9MpD1s2H3E=",5637771180446738092,-3411031503658629255,6508712824141236902,-7465410768000683177>()) {
                           case -351962203:
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

      switch ((int)com.yiyiaddon.m.b.a<"s35w3qpov2elot","RWpq/i0A4p6eBcUWxiWpFUQuSmDh7MvwWDq4xhch4WU=",-2093275784981449800,6864219477139987241,7146008209861953041,-7794359111896550822>()) {
         case 1847021154:
            return false;
         default:
            throw null;
      }
   }

   public static void d(AbstractContainerMenu var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sw983u0wb0puv","YPkZyVgIlwVD5YiCTqd5zuvw3yXK0l1fWHMTFAZoK/Q=",823383048963506408,435388262647771930,-371633321364718407,-1580632827150041227>()) {
            case -1613984986:
               return;
            default:
               throw null;
         }
      } else {
         a.a(var0, 2);
      }
   }

   public static void close() {
      a.cD();
   }
}
