package com.yiyiaddon.e.q.c;

import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class a {
   private static final Map<VillagerProfession, Block> aC = new LinkedHashMap<>();
   private static final Map<Block, VillagerProfession> aD = new HashMap<>();
   private static final Map<VillagerProfession, String> aE = new LinkedHashMap<>();
   private static final Map<VillagerProfession, Set<Item>> aF = new HashMap<>();
   private static final Set<String> as = new HashSet<>();

   private static void a(String var0, Block var1, String var2, Item... var3) {
      Identifier var4 = Identifier.parse(var0);
      Optional var5 = BuiltInRegistries.VILLAGER_PROFESSION.get(var4);
      if (var5.isEmpty()) {
         switch ((int)b.a<"s3e57vlsiwjb6z","DanfdiOaH6/2vSlimhbOf5L2mwaKRNexcKXDO4GRtwo=",5076725530949257044,8715892016301004325,2907348935219568624,2443237242555794448>()) {
            case 319693444:
               return;
            default:
               throw null;
         }
      } else {
         VillagerProfession var6 = (VillagerProfession)((Reference)var5.get()).value();
         aC.put(var6, var1);
         aD.put(var1, var6);
         aE.put(var6, var2);
         aF.put(var6, Set.of(var3));
      }
   }

   private static void ix() {
      as.addAll(
         Set.of(
            (String)b.a<"s3lyhv52jwxlqm","8RWVRF15EzPiJqt44kONOkbq5dlma4vnMsXyasCnw+89s72C0S38d2o1T7z0pA==",2092991328726108789,6123303392309752391,-2331230331165992862,7077549255368669562>(),
            (String)b.a<"s1onn4t9cc8lv5","0BjiZ3W2HCPnfnFO/lkgWeYxf4ohQS2FAlGbG4f+Txgb3JRxjxw=",-2647697648543570875,5733591465049789264,-2032685633565230309,6251827113021823404>(),
            (String)b.a<"sdgwli7zcpuf7","VaDEtRXKLpUMC0jaCLXG2zIU6TJStkyGzudVk1UbcVGgOg0SROq23shIeykYU36mDpcU+LZPaJ1s3O3WInkitw==",-1453381975218725289,-2163106978219142680,-5298251579438564641,3230883089435545442>(),
            (String)b.a<"s11uexaebnmwa","VOqIUPuSqKsQvHEiCEzHio1BTK1wtk06gAX8zzehNtT+sqaVracI6A3hV2mznQ==",-5064339378164737569,82499021761996495,-8374491948146761864,-4210448227895410724>(),
            (String)b.a<"s1vvj9xgmfjbvo","6WaXNkdWzbgQO5MK3mD+m+h5kFtOHS/kMbEPhd23LnuqJymNJEBSCW5O7diBUbgdjH0=",-6790026422569879335,-2184490266963571962,6374981125214119985,2315714087537118878>(),
            (String)b.a<"s8572kobc2tcc","qSLFq4UB4ZD0gMpL5qmeiPyhgN+m3GX4w0YrpJI11V0H5XwE02rAdg0k",4197306941540675286,7651949908077234853,-3809265044127691118,-954451917614217599>(),
            (String)b.a<"s8byc87sswkgv","Fb9jl6gwhT/egWsxTdRin2DsfiJzO6HGpTIftJ8BZ3IRcmBpJxK0/DY/kQccpymKgleJzY/c",3664174321803156249,-2503491404542879733,7840983828217026053,1756457612547955085>(),
            (String)b.a<"s3jja4u0un98a9","+BWVoSM8To8cKzyRFW+1tNbuFOpbgUQDIppslU6EH1BKK6f31TmGDsq79H6MMbz+",1815638151959549503,6182888482141795911,-9039114901353638157,-1912300619460688799>(),
            (String)b.a<"s3u0w3c7hda23s","NeTA1IYCL+uzbRYAAeB1BOyI7NfrYsNIyr8j+NeU+bp7qGxLOazDsx85Nf34ntSm",-8077502355679248360,5567237653163508122,-7034435398430032461,4273832450925571631>(),
            (String)b.a<"s3n9arj9ig359","TiU/VjZtxhWEhVYh6eqNNgs71aFBF3/xnMvId34pDE6llMwdmIioyWoJ",-5047712396659552340,6394296902529280148,6953587930863700172,476412528068622227>(),
            (String)b.a<"s12pgpzq8tcc6j","12D6f0yM57FTurIVGLzTjZflE+HJ+4hbVgxzvL72aMsAID/jjC8=",9095980770521103709,7667441506033776283,2300712028540848582,652261334676982425>(),
            (String)b.a<"s25j0pcmb0du6","+AkIwL52la05Y7NOMV4YZWfUbPMGR+NAacqM7jHSUiuJsmwW3BM=",-7134330837986902043,6738714465305213414,-7234813218855358200,-7527550163566903178>(),
            (String)b.a<"s3kw8t9c1lh6gm","XYfouyh5DMrKV8kMYmIA0RH+jjrIbdnaentaZOx0WjnT4off3Fo=",-1648387457455360990,8263997247448703446,-6930985539298121961,8441322350221213706>(),
            (String)b.a<"s7bet8zfr1jif","IjA2Kn2U2ln9lsqqRfZkBrOQEThwG2FziHxJkbDOf2mtcKBBmT+hS2Y5/SM=",-7811024375719867291,-1948214721322837255,-248566721472707912,-2788452426619120684>(),
            (String)b.a<"sj9xpg6tvkg2n","RhbVM+M4kjOlTrrOEaxOG6I0H4tNL091+Dh39dUg5ZcqVu2b+bWH/3R9I7M=",-3824247900705690815,4300614928388525955,-7857860797978181790,1148619612877954946>(),
            (String)b.a<"sirj80pdf9cty","xbMrTw1Ol3X4GPtcDeFx8YQG6hwargcfTxSbwKp0TJaERnEZgJcPGSB+QGRewg==",8750595570389699987,7038087723203257808,-2783380983173937013,4112179510621363424>(),
            (String)b.a<"sl99d3n5463k3","+zitA5ucUo2aW/xjoAJimjlp7Q58p0UiAFsxNWS+MA2o5UUJgozE7qlPbWWVsW4O0nhMew==",-2781453630598641776,-6384062651192016740,-7968617867223860116,-8201599151439586122>(),
            (String)b.a<"s2qo2iwgje5osc","VsvI3oFS0hf/pXWzjS7HKDK2kO8B+kQPEYijZmwdoPOQD8xj9kwGsMM9hgYYjb0T",2676648322010083508,-8399380592103328156,-3341570859853438563,2419296843520194647>(),
            (String)b.a<"s1wod8bxlpwpxb","yhfdEJBcyUTaIEd0RckUGjJWofSGpi7Eami7KrpMmZQJpVB2pfXQZYdoDWSwwMJwGD+32zAT7csAsw==",747381974206470182,-8764025387263257019,8813326449276890093,3305278816064171502>(),
            (String)b.a<"s1gscbcu4sccgf","4ZMD9j26EftbtsK/ayQQP9T96R4++5KviqRBtS9abXzA4bajCkRutFmxwfDfAC7mBz+AnR8GxLYZdNui",-7517142748971960301,-5135942822958267715,-8367446135719507489,4672172886733687789>(),
            (String)b.a<"sbq2w6mah6yj3","w222K0KYZN6te+xmW42Pzy8pp4gDbUe8Bt9DLXP0aQ8te+H/Qx3uhL+qeZkpPlQEuL3r0+nDV0BDv9D5WPF2ozilu1Fd5Q==",1430031299572977299,4829209129697337408,-1725955693023094271,2147448993278311524>(),
            (String)b.a<"s1xjbnap3psitk","WmTrsiuPGUsO4yekalts0F695z5q+YI496NgUT26/PVHLgV8EdeQo6EqXrMfyhI86Yqq4z9Xasj76g==",-7997336394878286161,-2455373432483010123,5862640296557784296,-5203985525349778137>(),
            (String)b.a<"s3s5gkofkzsa13","1CXpFgGBlehRWSUZDL2B02W3nXWsk0Of8ZcNAHM+CyVfx0krV3AhjQ==",-1945964337915823137,-7143010951871170361,-1543581844124357312,-8956896803917233233>(),
            (String)b.a<"s3rb9mj45qj17c","rl3lwtmiMEGvbuUK1nd/syFuHNzNj1WhmVbSO47QKv7Fo7fQkKcP1mswDPYvZSaU8Kg=",245854519710985654,-3720085460256876030,7792045910597435123,-9161549548933551977>(),
            (String)b.a<"stx1bjw9meptx","DLG8aCVMb79nMNfwq+s1KXapTQ+FZOkCAaHswcvWB4PBkH7TecsA5n/qPsrJ1v/jZABFxSNl",-4177762114474571226,4289570047278953922,-4391540854365700326,7503634503565495522>(),
            (String)b.a<"s1oxhmur3jhjri","4znz1U2bC/lAfAaHFwP6mtg02Bq5VSrZuAL806C4Dn1ec6escNg+RSW/NjcjZvULqxBo0a88",-7040088171873762763,-8032712581101559146,-6520289019096608838,4809117793804593116>(),
            (String)b.a<"s3kqj41c937zjk","/bcLngZu5UWY+ysMf/YWmzNjjJuttBr1HqG8y9QmLHPEmqUp0rPWYft6JINpFoVmSb7Vgg==",719030970566950432,3802421878037575247,-6930250223927303336,1879037280206396221>(),
            (String)b.a<"s3pqc2nz3xqx2s","aGQ/Zl+bGMhBa2+405hzNnLSh5YK+lV39AL1vtOu42QrvzHftHe06udIq+CbEi1H4IIfGJd+1OmZ0w==",-1076849572648700835,-5387713261349606486,2176431437087265020,1907709566546352399>(),
            (String)b.a<"s3sny41px036p0","AaKgKjXNk8IPrvIy+vq1Ujdmi8fJBLcA5N2miW2zqvcnFTYq",-5659424277734883521,-8428096381368241524,-4039040985901618190,1038412927067503445>(),
            (String)b.a<"s2pcg9l4vpw4fb","1FBwagq4RQpe1KHknjnV5M9uOO6yqE+Rd6Ok/tVG0pUKyI1jY29+AFpl",6682481377060320557,4525012661851408741,-9072480064210445584,-3918727542758287131>(),
            (String)b.a<"s3tekwgu32s9pb","idOIKV2qKyLbEHaVO8X6uscDNrB4RXd3yAAcmGdo3zCaSKL3n9XhKEKmNIXd2eXK",-9075596603938610136,5813553871159052317,5115890451940283155,2459238175878134242>(),
            (String)b.a<"s1gpjr3p8jho2v","xt1qAn0eGh4DXNr1RkEJWLkBo1xDUItlJwx6uoD73ngDpedkgJjXNFwV",4150846714441145446,7860637152603638010,-2285282593756715004,6301283682941431655>(),
            (String)b.a<"s2g0ykd24rnmjs","1cnCd3fWk6dLONN0FYSZrMcYJoqjoMnDYXE3NfTyaEBi6FldhrHQEGhTU64=",6053030372368233034,7869415263201421803,5365772381515321320,-1907846944467715951>(),
            (String)b.a<"s2sapgpe28qasy","Jaqc7Z1uOppWa7fviaw17k1GC6Y3qhaK5PUjFtJw5Z5PKCnAqnDCk/XI",3521471411177491243,-3098973827103272439,-7020860561266656377,1281937894644954518>(),
            (String)b.a<"s2b56bs83vow4c","reU7fPc9askc2J14WeMGPNHU+atCyUehFk10lF80W9NL/MAPfyHTnkxSt2pPaGfD",-7988419568633308097,9186158741220159545,8570147746744610730,-8519459706435370675>()
         )
      );
   }

   public static Set<String> y() {
      return new HashSet<>(as);
   }

   public static Set<Item> a(VillagerProfession var0) {
      return aF.getOrDefault(var0, Collections.emptySet());
   }

   public static boolean a(VillagerProfession var0, Item var1) {
      Set var2 = aF.get(var0);
      if (var2 != null) {
         switch ((int)b.a<"s2uuxtate6d7w2","6nhVYGWuSPpjgJDo1/64OZnuHhr0Ez4Z70VQQD5yOrE=",-8973037185400381437,1740515429847688595,-10346686406310405,-4206923911691831241>()) {
            case -1315872887:
               if (var2.contains(var1)) {
                  switch ((int)b.a<"sbjev43fgsghu","ULUOLmKxzusAb/NCxlHfBoFbLv7HmvR0sv8xleO1xXM=",5186274503216268058,-1949199169535192091,5456111005023642887,5617293065296363298>()) {
                     case 1626508552:
                        switch ((int)b.a<"s206rudaf8p09k","cuRAZ4Jjauk+LQo+43IDEMKQuD9Q+a/pRUkg1BsEmWE=",-3955524297398865349,2734927164759130583,-6237979827558647948,7001915740647583929>()) {
                           case -429202137:
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

      switch ((int)b.a<"sj3wzn124peqr","80kQ1STUa5Jx70dtdg+z1O2ca4G5+gEznEqFoa1ywEs=",-2996357937839540363,1631135641433923732,-398307408768600527,8786764969766474254>()) {
         case 1104843588:
            return false;
         default:
            throw null;
      }
   }

   public static Block a(VillagerProfession var0) {
      return aC.get(var0);
   }

   public static VillagerProfession a(Block var0) {
      return aD.get(var0);
   }

   public static String a(VillagerProfession var0) {
      if (var0 == null) {
         switch ((int)b.a<"s3q6nvte8chldg","FiJ62hz32YeQeDbNdL1qDRpAn3BQ0iblRDFJ539AW9g=",-202448427753914192,-6071574167976013427,7428176907033240696,121524581799831951>()) {
            case 1592917072:
               return (String)b.a<"s159a6oatu944z","4e07PHPbAkGGKtpQKog05ZRjZxPdtRj7Wgq9HOVL9VsVg2a0",-7212445168595048789,-6805096820186524730,-7349192599700698984,8578367808870419074>();
            default:
               throw null;
         }
      } else {
         String var1 = aE.get(var0);
         if (var1 != null) {
            switch ((int)b.a<"s1ack5br67csg9","h5NFxSOtvTYOLzTs1mUUmuNgp6/OkGtwVZg/0Ati//M=",-8485960962565437475,3626546266573208377,8490635610267198525,8552574146463316990>()) {
               case 1719341712:
                  return var1;
               default:
                  throw null;
            }
         } else {
            Identifier var2 = BuiltInRegistries.VILLAGER_PROFESSION.getKey(var0);
            if (var2 == null) {
               switch ((int)b.a<"s1gywt3rc46jqk","FZ2J4xQdj0FCtgzdknXE6NYRwWdhXugFiFL689UEGhc=",3152975886441000287,-8664218276951081910,-3000191384871704458,-4888661389572554433>()) {
                  case -2088617030:
                     String var10000 = (String)b.a<"s159a6oatu944z","4e07PHPbAkGGKtpQKog05ZRjZxPdtRj7Wgq9HOVL9VsVg2a0",-7212445168595048789,-6805096820186524730,-7349192599700698984,8578367808870419074>();
                     switch ((int)b.a<"s3lnttidlphqm3","uVqlnU1A4cd4ipzB5RdDrBnDb5U0OmGj5NrSMYwA708=",-7973037239208175796,1368373702424838058,2923669463608720188,3510755424003882848>()) {
                        case -1192605478:
                           return var10000;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               String var3 = var2.toString();
               switch ((int)b.a<"s2hxljiqpvdz8m","jrhQFXkue05U8994ldlDnKjqkwJTraQWwhGqsf5frPk=",8195752848918774929,5583558575698814468,-5145785264249244545,-5085351928488000785>()) {
                  case -1956436094:
                     return var3;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public static boolean a(VillagerProfession var0) {
      if (var0 != null) {
         switch ((int)b.a<"s30e2bshzgzlnw","AdLSyg+hw9HHCGPoRLPrig/A0B7ljtG3W9sSom8Q7co=",7609445425405280295,5960409445066385054,4036470765642240728,-7134922006562599731>()) {
            case -847980262:
               if (aC.containsKey(var0)) {
                  switch ((int)b.a<"ss06p5rcejwvq","1oVcPNxmAY3K1ND9EVmQljaxET9aXgg8MOUDjPfdFzM=",-1783989722583650578,2648951744943693008,-8350214942544971852,-3590506297127814354>()) {
                     case 2093641310:
                        switch ((int)b.a<"s2dc16l7n4byag","ERAnifdyH0o79ffmhCujm5ibOLbfPff42Ux/+o+o6m8=",-1990461621087995460,8593931184279871837,4705418624365044497,-2196256443987520016>()) {
                           case -1494211372:
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

      switch ((int)b.a<"s2wvvzxieraadk","lJdxS0bdjhghTNf9k55EF40z5rCwAgRcZDHOnw1t2B4=",629680935438694376,2432704297817244484,3352276784446078780,-2757914117429749796>()) {
         case 2017526055:
            return false;
         default:
            throw null;
      }
   }

   public static List<VillagerProfession> bq() {
      return new ArrayList<>(aC.keySet());
   }

   public static List<String> br() {
      return new ArrayList<>(aE.values());
   }

   public static VillagerProfession a(String var0) {
      Iterator var1 = aE.entrySet().iterator();
      switch ((int)b.a<"sweqw1x9ku9j9","je83WmA4GBLFyn8O5ORfRZvijZ78bbsYQqlc+jmzP+k=",652362573059418849,7371567738708686695,-4867325501590481153,2140499222341310146>()) {
         case 1686611866:
            while (var1.hasNext()) {
               switch ((int)b.a<"s29vxjq60ofwps","Y3/etpmCs7NB2PHoVBhj3VIom7TwvQvCdAbZ33jluhQ=",-3690878820701407285,-7522143264992199098,5519394356142764283,-4903152285581841995>()) {
                  case 1367742831:
                     Entry var2 = (Entry)var1.next();
                     if (((String)var2.getValue()).equals(var0)) {
                        switch ((int)b.a<"s3flhjtyz4y6ti","WRpPf7X6RZA+DTUJAK8yimxBfKWxt6cPwVTjVwvc0AM=",5946466725283135807,-4420076946200202534,7907677226853957037,-675771245687485112>()) {
                           case 1731095345:
                              return (VillagerProfession)var2.getKey();
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s5bmgsp2xd6td","DFKquuU7hrjyHDoman8ODVJcpKjsrx+FJ+n1teE/jCc=",-7755417622029252641,6571597806677257305,-5486084235472053530,-3184683774808659624>()) {
                        case 875810600:
                           continue;
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

   private a() {
   }

   static {
      a(
         (String)b.a<"sk6aswx3e6f65","mdpEj3IK+ikIALKDRtAjJt5tV1CGW7BPJvzlFdW33+ws8FzWOcWTteNy6SKajyIxgSMqhyI/4xhjXeT8Isw=",2822048123038915311,4066701351384706978,-8099218760107465020,-6133164360838059002>(),
         Blocks.BLAST_FURNACE,
         (String)b.a<"s3sl95byj9z3g","AggCf1eEqNCQgeaz9SzSP+lVJHZF5zJU5CWs8Un/YJ6llA==",-3004306884681694859,3255837218150013749,-7607047806610984392,7657876900068649243>(),
         Items.IRON_HELMET,
         Items.IRON_CHESTPLATE,
         Items.IRON_LEGGINGS,
         Items.IRON_BOOTS,
         Items.CHAINMAIL_HELMET,
         Items.CHAINMAIL_CHESTPLATE,
         Items.CHAINMAIL_LEGGINGS,
         Items.CHAINMAIL_BOOTS,
         Items.DIAMOND_HELMET,
         Items.DIAMOND_CHESTPLATE,
         Items.DIAMOND_LEGGINGS,
         Items.DIAMOND_BOOTS,
         Items.SHIELD,
         Items.BELL
      );
      a(
         (String)b.a<"s3u4p99l2nzw9f","x7MKJBDjlotoDuRnKs99khJBzF2cwlpL+mPsXdu5TehO0DETjmz61aDFMZZgoCbg6ZMFjcsFpmsoJhuVvxI=",1162970291894669197,-8137638005853430520,7843928112456151077,-8795964254323651511>(),
         Blocks.SMOKER,
         (String)b.a<"s2o933fxsm7qji","PxhyfNAlJcKGQrPCBZnAoayqtHaqBiboq03T2N2YHM0=",-7002040414826304649,1633451004633747213,-2052210622541998130,5551661525548076760>(),
         Items.COOKED_RABBIT,
         Items.COOKED_CHICKEN,
         Items.COOKED_PORKCHOP,
         Items.COOKED_MUTTON,
         Items.COOKED_BEEF,
         Items.RABBIT_STEW
      );
      a(
         (String)b.a<"s1v7zioowd6668","TguubQOzycfsoiPQQD4uk5Zat8sSVU3wsOVyszkRh9g1CLMh688kG1e+wb8wU0k4oSrXJrDEQSAcwtqktdxixlu/LWbIOwNZ",-533518222745762407,-6540016942435230461,8019468515180183048,-6649415696271440085>(),
         Blocks.CARTOGRAPHY_TABLE,
         (String)b.a<"s998ywoi5vhwa","LQ+5uhwZMuTjPlmSerhSf7A95ego/M3WmRZ3G7loZs4Drw==",-2794900088412624344,-464418293371973697,8890337784955146296,-1200153182078393754>(),
         Items.MAP,
         Items.FILLED_MAP,
         Items.GLOBE_BANNER_PATTERN,
         Items.ITEM_FRAME,
         Items.WHITE_BANNER,
         Items.ORANGE_BANNER,
         Items.MAGENTA_BANNER,
         Items.LIGHT_BLUE_BANNER,
         Items.YELLOW_BANNER,
         Items.LIME_BANNER,
         Items.PINK_BANNER,
         Items.GRAY_BANNER,
         Items.LIGHT_GRAY_BANNER,
         Items.CYAN_BANNER,
         Items.PURPLE_BANNER,
         Items.BLUE_BANNER,
         Items.BROWN_BANNER,
         Items.GREEN_BANNER,
         Items.RED_BANNER,
         Items.BLACK_BANNER
      );
      a(
         (String)b.a<"s1fj567k2uta12","+cTMjfSU0thJ3EOxDSqIHRtCHkfi9f8DjdsrYo38ecLQz3Pj4mRDSabi89FZPwvftxJYgsjUp4PKjMNO",7383148800412461017,-2526509870680167170,9088922509635751473,-5851026192826539625>(),
         Blocks.BREWING_STAND,
         (String)b.a<"s150htjtjsgo4","5zDOanY8NlTlI62azux+AclV3BdCp1zuFbMJ+ggWByw=",-3314819521175894782,-5537146589157256741,7317244173883531442,-5993720937078166664>(),
         Items.REDSTONE,
         Items.LAPIS_LAZULI,
         Items.GLOWSTONE,
         Items.ENDER_PEARL,
         Items.EXPERIENCE_BOTTLE
      );
      a(
         (String)b.a<"s35mpyb5bfssql","zZoct630rLaVEDSFphQ1DvMyDqCVxcyYXKlh3iv0ALPgYuadKRX4cwMnQ4kBjJSUDjh94Q2TNCfcmzo/",-4174679549358723014,5561315215342615216,-1816231632737894843,2061978263312554590>(),
         Blocks.COMPOSTER,
         (String)b.a<"sltcxbtltm920","xrOwHITqcRJi9ZDNbmEiusu/Ouea3VJkHlLbnfYjrLA=",7751211373643503421,4254340217490204790,8702498471753512260,-4331924591610068050>(),
         Items.BREAD,
         Items.PUMPKIN_PIE,
         Items.CAKE,
         Items.COOKIE,
         Items.APPLE,
         Items.GOLDEN_CARROT,
         Items.GLISTERING_MELON_SLICE,
         Items.SUSPICIOUS_STEW
      );
      a(
         (String)b.a<"sgtm4u6424dgr","NOI21sPdwNhdnUEhq4c28rRHGswfI3rGZR3DXD5sNQO4mi5T5c1CWJkMSZizi+8+LdCmIrwOgY2mXbaLWmNuT+bi",-6808097519667992886,4609616600700817934,3682983244219962227,4893768647124111757>(),
         Blocks.BARREL,
         (String)b.a<"s1mjhbcpjwx1pv","E61obmbi2sGpRRJGvie+b/e/xX833SdnzTTsFSmlkjg=",-3757664582172987911,3007653235960422428,-1136390071652101057,7991400181796201730>(),
         Items.COOKED_COD,
         Items.COOKED_SALMON,
         Items.COD_BUCKET,
         Items.FISHING_ROD,
         Items.CAMPFIRE
      );
      a(
         (String)b.a<"sgjkpq3o3mcfk","a9Z66C6k5rwBrlTF4zsWVDAYnPjGROTJsx0Tg+TkT90BZZqHJcC0x/o59XmFEtPHON9Gy9gWCnpFpfxLn/jK8Q==",-692344607456169578,-691345276902288086,3008179673601580623,2789475254721334455>(),
         Blocks.FLETCHING_TABLE,
         (String)b.a<"s3viq0sl7o0p2b","vVnifUK4BxrW5x766BVtcsOIqhB/nC94TjRIFn2242qL2A==",-4974593861745133033,2689907650319212502,6414925117972045122,7693071191880087945>(),
         Items.BOW,
         Items.CROSSBOW,
         Items.ARROW,
         Items.TIPPED_ARROW
      );
      a(
         (String)b.a<"s3nbfiwypftnd7","WsWRP0N6gl3GAKZq3Xco3Ln5s2wCAOW+7qj6fdrrPSYxVtkdmcDkR4kmAPemmwBG7urDzDeHlQo01o/1Odtj4RwDk1LPKD6Ojjo=",4340873166023957655,8832833474893479756,-2350894410875209563,5702683196566801014>(),
         Blocks.CAULDRON,
         (String)b.a<"sdca9hlkoha8t","CrpuJP2t1GOskZ/+GDWSdm2Jx6mbg7JjWvuD2EgH75I=",-8690129611281576089,1094063920616325157,3120537671438802761,495882848429845291>(),
         Items.LEATHER_HELMET,
         Items.LEATHER_CHESTPLATE,
         Items.LEATHER_LEGGINGS,
         Items.LEATHER_BOOTS,
         Items.LEATHER_HORSE_ARMOR,
         Items.SADDLE
      );
      a(
         (String)b.a<"s3uptvjy1wb6mm","cMcU+EJxi+YGI4n3Odhdusoe/c53v1GzMqn1JrtgIhgvF1YuvABOx0I3aF4nos5xusPZq9b4FmAkdMRVqZGccdpF",3727459014115419534,2913983325894896644,8974922082164849146,968329339987039018>(),
         Blocks.LECTERN,
         (String)b.a<"s2ln69w8k0o6oh","xvUniwC0oXWtPlCXw8rOLXk7LJboBXlYDu5sRX02MVyksMWwdu4=",2109331771555067270,-2945459240494872545,1926840544645891050,-2234781395455620832>(),
         Items.BOOKSHELF,
         Items.LANTERN,
         Items.GLASS,
         Items.CLOCK,
         Items.COMPASS,
         Items.NAME_TAG
      );
      ix();
      a(
         (String)b.a<"synhhlk60lwue","5Wx/BCRgpMiNS9vhZGL0Ply7IBz6UP/1vH6Qhw3CTe68Mi9/6+baPer6B6xMHv6bN9W8R9q82cG6uQ==",6382086311355814074,-2009912020432198136,-2257682889238574735,-3034841250736323743>(),
         Blocks.STONECUTTER,
         (String)b.a<"s19jtgcs5m2h6j","GPZNBU+5B1b2Q/MgbYOVJHc8kDNI+e7Cd8YSCE7Cr4k=",5146534043336837519,-6273988065338601742,2152327796244584084,-6235525477862364561>(),
         Items.BRICK,
         Items.CHISELED_STONE_BRICKS,
         Items.POLISHED_ANDESITE,
         Items.POLISHED_DIORITE,
         Items.POLISHED_GRANITE,
         Items.DRIPSTONE_BLOCK,
         Items.QUARTZ_BLOCK,
         Items.QUARTZ_PILLAR,
         Items.TERRACOTTA,
         Items.WHITE_TERRACOTTA,
         Items.ORANGE_TERRACOTTA,
         Items.MAGENTA_TERRACOTTA,
         Items.LIGHT_BLUE_TERRACOTTA,
         Items.YELLOW_TERRACOTTA,
         Items.LIME_TERRACOTTA,
         Items.PINK_TERRACOTTA,
         Items.GRAY_TERRACOTTA,
         Items.LIGHT_GRAY_TERRACOTTA,
         Items.CYAN_TERRACOTTA,
         Items.PURPLE_TERRACOTTA,
         Items.BLUE_TERRACOTTA,
         Items.BROWN_TERRACOTTA,
         Items.GREEN_TERRACOTTA,
         Items.RED_TERRACOTTA,
         Items.BLACK_TERRACOTTA,
         Items.WHITE_GLAZED_TERRACOTTA,
         Items.ORANGE_GLAZED_TERRACOTTA,
         Items.MAGENTA_GLAZED_TERRACOTTA,
         Items.LIGHT_BLUE_GLAZED_TERRACOTTA,
         Items.YELLOW_GLAZED_TERRACOTTA,
         Items.LIME_GLAZED_TERRACOTTA,
         Items.PINK_GLAZED_TERRACOTTA,
         Items.GRAY_GLAZED_TERRACOTTA,
         Items.LIGHT_GRAY_GLAZED_TERRACOTTA,
         Items.CYAN_GLAZED_TERRACOTTA,
         Items.PURPLE_GLAZED_TERRACOTTA,
         Items.BLUE_GLAZED_TERRACOTTA,
         Items.BROWN_GLAZED_TERRACOTTA,
         Items.GREEN_GLAZED_TERRACOTTA,
         Items.RED_GLAZED_TERRACOTTA,
         Items.BLACK_GLAZED_TERRACOTTA
      );
      a(
         (String)b.a<"s2dozor589nxnv","bS3E7Z4bRFPM2o0kgKDqM28TJiZmBQEhhLb9aNlPWkoyvQe/4e89363YYoPwCVr9ThoB4q9TrOUnttNeIGQAFQ==",-2974455378172860442,-1100349327689932054,7321270940434965500,-1947812263598065999>(),
         Blocks.LOOM,
         (String)b.a<"s8lhinoq0aupp","0n/NDgm29aCar4wsXZPZS2EwraryJWlJqKVola/hTicfcg==",-2796656627749805511,2875967198371164179,7529674242957865009,-3637808035494348665>(),
         Items.WHITE_WOOL,
         Items.ORANGE_WOOL,
         Items.MAGENTA_WOOL,
         Items.LIGHT_BLUE_WOOL,
         Items.YELLOW_WOOL,
         Items.LIME_WOOL,
         Items.PINK_WOOL,
         Items.GRAY_WOOL,
         Items.LIGHT_GRAY_WOOL,
         Items.CYAN_WOOL,
         Items.PURPLE_WOOL,
         Items.BLUE_WOOL,
         Items.BROWN_WOOL,
         Items.GREEN_WOOL,
         Items.RED_WOOL,
         Items.BLACK_WOOL,
         Items.WHITE_CARPET,
         Items.ORANGE_CARPET,
         Items.MAGENTA_CARPET,
         Items.LIGHT_BLUE_CARPET,
         Items.YELLOW_CARPET,
         Items.LIME_CARPET,
         Items.PINK_CARPET,
         Items.GRAY_CARPET,
         Items.LIGHT_GRAY_CARPET,
         Items.CYAN_CARPET,
         Items.PURPLE_CARPET,
         Items.BLUE_CARPET,
         Items.BROWN_CARPET,
         Items.GREEN_CARPET,
         Items.RED_CARPET,
         Items.BLACK_CARPET,
         Items.WHITE_BED,
         Items.ORANGE_BED,
         Items.MAGENTA_BED,
         Items.LIGHT_BLUE_BED,
         Items.YELLOW_BED,
         Items.LIME_BED,
         Items.PINK_BED,
         Items.GRAY_BED,
         Items.LIGHT_GRAY_BED,
         Items.CYAN_BED,
         Items.PURPLE_BED,
         Items.BLUE_BED,
         Items.BROWN_BED,
         Items.GREEN_BED,
         Items.RED_BED,
         Items.BLACK_BED,
         Items.WHITE_BANNER,
         Items.ORANGE_BANNER,
         Items.MAGENTA_BANNER,
         Items.LIGHT_BLUE_BANNER,
         Items.YELLOW_BANNER,
         Items.LIME_BANNER,
         Items.PINK_BANNER,
         Items.GRAY_BANNER,
         Items.LIGHT_GRAY_BANNER,
         Items.CYAN_BANNER,
         Items.PURPLE_BANNER,
         Items.BLUE_BANNER,
         Items.BROWN_BANNER,
         Items.GREEN_BANNER,
         Items.RED_BANNER,
         Items.BLACK_BANNER,
         Items.PAINTING,
         Items.SHEARS
      );
      a(
         (String)b.a<"s386i1z6fpsu9v","rBFo28T924W8VhH3KHhQbHNvohVmIVAz8yt5tPEi2LCLDi08alvbCXrNPjF3z+TVBWE3EHjl4eEk0gTpND6bvmqX",-4073507082621143842,-4368272676344092203,8449040623153564125,57187588016199113>(),
         Blocks.SMITHING_TABLE,
         (String)b.a<"s25q1sjruhg1uh","IK1RidKmdA6bYujsZo111vPpfcPzy9exN1X12WISpUUY2g==",3941612585330434996,3091542931069957412,2414462514368523856,-5028478145200020266>(),
         Items.STONE_AXE,
         Items.STONE_SHOVEL,
         Items.STONE_PICKAXE,
         Items.STONE_HOE,
         Items.IRON_AXE,
         Items.IRON_SHOVEL,
         Items.IRON_PICKAXE,
         Items.IRON_HOE,
         Items.DIAMOND_AXE,
         Items.DIAMOND_SHOVEL,
         Items.DIAMOND_PICKAXE,
         Items.DIAMOND_HOE,
         Items.BELL
      );
      a(
         (String)b.a<"s2ugrkmyfgyezy","7sm9GntWeC3dKfb3EveaOMHnW6/RfGRaPsBm6wSKXFsKz9a7t+3bYMtM6yqVWX5Kx4jqKlBaUwj/u0eW9uYTjz9p2AwIEg==",-4336774715767862067,7247987883936151191,-9125128490559406951,7909801870286794413>(),
         Blocks.GRINDSTONE,
         (String)b.a<"s2h9ogxdsl65d2","9DoU80JSYv2s1g5Jyb0pdDN4hRTiphKpq6tFHf8C7TX3cw==",8578801806389041504,1245559014359590790,8001546050757168015,-3602032391042240735>(),
         Items.IRON_AXE,
         Items.IRON_SWORD,
         Items.DIAMOND_AXE,
         Items.DIAMOND_SWORD,
         Items.BELL
      );
   }
}
