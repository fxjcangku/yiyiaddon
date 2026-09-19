package com.yiyiaddon.e.j;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.j.j.c;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String mx = "mining";
   private static final String my = (String)b.a<"sffwp7tjxiet9","5BXa0iMEiLU3zK3Md9+vlbPjW2KT27DFQdwmqRsEhVD3tbaKTDP3x78uly8zI2DNcxQf1g==",-2145720146438974270,4402151203984070223,5340063181538659317,-1284088333499484692>();
   private static final String mz = (String)b.a<"s27jrktic79j5u","jOXR+RjBsLGYO9J9NzvxUNRbs0Ia3K3fp9oMRhceg0iXfuDrUpQCS6VAFmXs6P+fAP76M1rS",7336526291088573551,-765634091826786105,1462960087283087902,-1724845080316237797>();
   public static final String mA = "自动挖矿";
   private static final String mB = (String)b.a<"s1gv09ehj54bc8","7KH+YxdIrobDWLYcV80qxHrGMmFApaCyCEdoghhB",1814486194785275211,-2563496550516562759,5490491485493096199,1085928804646361558>();
   private static final int ga = 36;
   private static final Map<String, String> I = Map.ofEntries(
      Map.entry(
         (String)b.a<"s17ckerkf0ilwj","GhOPvMhb4Pp56jW5d9l3wt8Xqdl0pd7EpNBto3Hj4tHBqlj7pkC+migJC4EBxgIDohwF8PS/boz87zuT+Q7c8g==",8768996336479758221,-9178999513608288380,1878275290778519022,-2220238530521419244>(),
         (String)b.a<"s2duhq0kke4dsr","A3MqyVH2hU8IfaQ+ABKSlJ9lBQRh/WY8MgIqjcEjfndilW5e/MyPo90zf5quu1++v9DMmRxkW4uyOeKwwWYY3A==",-4124374979104907088,3975466761747004158,-95243776675567926,-3369894527568328199>()
      ),
      Map.entry(
         (String)b.a<"s1sjuvjy28qcuu","pIffm3SsygvjC+u25WXeC5AYc2tLg97KpJnlCEy4K14xuTN6XK2n0okzzDvRf24MiYO1drOy3H3wq6uodyGzIw==",-8855995482306774966,641694092045341826,4595857610520646424,-8335241098910824164>(),
         (String)b.a<"s1fo8ej8b46e8t","u+ShF5bqnz+EVIvCWovaeYA2sLT3UhmWTQUVJPbyj057S6qVIa22LhCGWmrK1AJzbDSQuk1ugJMTDOxYSgarTg==",3062420745608930692,8761435442276424881,8545713328308877701,-1767639689598994720>()
      ),
      Map.entry(
         (String)b.a<"s3jhe9702sbme1","uwzSO2j1/6dbndrR3sxflrlXkUWa+azwzg+CfmBBlTN5lABa3Ia7Ppe9tI9rx55wyuMMvWRgPcEh+Y0JdUa7AhN/SBg=",476429665867849343,6061171761193566253,5851930448502718805,4834162702252976164>(),
         (String)b.a<"s3equjwfgqj86m","3jB7o1l3JwDiGcWKxJvT/bgjoj3l376tWAE3DJFXduddnqZZXSVM+45JwelnetZaEVJDUHLfbljlk2/H4LwTOSe5OI8=",5379102620128584824,-6100027292833611777,7490742414755377941,-2339969267393156659>()
      ),
      Map.entry(
         (String)b.a<"s2lg5j53wcikeb","uu2X4ahhldjTrPTaiXUBbPgrmtoc37gJLxRAmowQpuq1Rn398oNpOe1h3goJtPetgbeRu6JMmFPMul3m5/KIYg==",7727562964998112170,-812250889424538834,-2111643228455941223,7434007050085741099>(),
         (String)b.a<"s1z7nglvzzfiqu","BdQOQmx0afoBc55gjAQT1ZcUni/j3BCXAxxgtvQpPZfSdeOwSXdyzzSISWhq4d0VWPrSqjNAVACzMMEwludBKImX8+4QD7EN",-7734046282760442755,8014848299988322616,-1937198888650605977,2383159099375574667>()
      ),
      Map.entry(
         (String)b.a<"sn3iu6s7d6bsz","BrUMb9JRGtB1k8pRYzVPY6tdlh14GMKj4671g0u4AkbnNtVnCM8Q09LHuOD/GMkiUV0e39u8lqZYDLCQs0LbPm2E4SVy7NDN",281483563462208219,8117331826110179964,3125583712753355004,-8197553832108868630>(),
         (String)b.a<"s3r7miew28n7b","voce0TnomocBgVUjIrqCIclRtkXZ90Ms6+XDHAz3eTLA3W74COSsaH70eKPMfXApMJkpjuSuVIZ/DLxeuvfT3nsO",6168849022338495370,-4337131586401056548,-4617313672681996134,-5799423397260663533>()
      ),
      Map.entry(
         (String)b.a<"s1w2bru0o19ls5","ldQIpr5zmPoN7s0qBiogqd4N+8BEpWj3dJz/mSIMQQpraMz+FeULdHWu5GvzURwCA0IhOIH8O73FoKrsq3I=",-4505910899860552879,594681155622939193,-6616889168540175758,-5156622317395460274>(),
         (String)b.a<"s2wlwirxff5pj0","uO42yHhv2MVSuiBnr2cvnAOWTAB42yVJuxj4JSed/17hVX9UVhWBWZPvnPOCeuv+cR7CGAPO0bUshoFaykW8yH0gSY/aDA==",3317582430671586279,-2437568388963271459,2118076833122120526,5333556818920940169>()
      ),
      Map.entry(
         (String)b.a<"s2fnxagekiwrub","tunxMCfwcoyJmi26oQ1vgJ0Pd3l+Uha0UPjWUXTVgoobGGRo9iPvCck3VW3DHtF7L6Riw0ozFlFu1UWKylc=",-8842447471441803191,-3251347474580231159,6832164727921591149,7264792604456157532>(),
         (String)b.a<"s246zmykpoz2e1","zf58j1Ry1nuf0cRW+3cRED1J3cdk/RUL2QbIVvdK7Oee+PZlQEI1gPDemt844tUuf0AV5FM5lWSRIyf7PPJe66k1L2gI8w==",8845827451865997183,2912785828823118171,2190022030340828244,5614976714535290065>()
      ),
      Map.entry(
         (String)b.a<"s1k9xqnfw9ehcm","TtzgpoLT8eMHFkduBnPnTS3XUuhN2wF3xQtEDCTsWG3RNqvIgdR0owJyGSufb9QFqvM7ZyIBjXQ=",-5013537140872109234,29615402573571297,2291593179266035865,620041333646110793>(),
         (String)b.a<"s1sfo211vtbr3i","BwHJcRMkcsSbME21riuQPZdhNuYYulIQFpsa4i4nf37SOBr0FCa1NPLCKzpDH/C0tDtA75LrBDYrTV6b04aurA==",-68337637975590963,-3543465755785158807,7546900469671473829,5851637888415216457>()
      )
   );
   private static final Map<String, String> J = Map.ofEntries(
      Map.entry(
         (String)b.a<"s3uhfacwd4q0tl","wYphFKEpYCaV0A0WgVzOUArVS/z7CY7g2xQktjus+aa1JJS8ZViQBspC/IWdFZhV9vwg/KGQOtMofLagqAYPk98t77D7KyWdc0ZUDg==",-5656496703406750526,3411260926892359713,-8741020600295213764,7801181933849744701>(),
         (String)b.a<"s3uhfacwd4q0tl","wYphFKEpYCaV0A0WgVzOUArVS/z7CY7g2xQktjus+aa1JJS8ZViQBspC/IWdFZhV9vwg/KGQOtMofLagqAYPk98t77D7KyWdc0ZUDg==",-5656496703406750526,3411260926892359713,-8741020600295213764,7801181933849744701>()
      ),
      Map.entry(
         (String)b.a<"skr944v093h70","XAPW74MtqJfXzTCA6tgDONkKPWnA6e4Y84SGoZd5SH1A+DK1WkMdhhbRp4CLLGME0Ox22NT7a41hu/j66DhVJv+AqrrJBA==",-5722947723894694521,-6352352355779036676,9161272848675377622,-3837496928126081125>(),
         (String)b.a<"s3vm36irsql0un","bbUVti9jAvvLtIMoezPKBMUCDPOPR4+PjCjR+1+aeKl8rIXSOTEEQucmhBsu9hdwx2S/GKdCM0fI81lKxzGefbiCpuEnCuuVuQhr0Lk0",8660067117550669922,-6361778220543300984,-7710442031078562216,704516571987372295>()
      ),
      Map.entry(
         (String)b.a<"s3dzn183ckw0zk","SLNeY7TGH/EZ4xlPaPANby8ybOeEHNSbu6e/mfifbG1NEahkvRFUx8hlI3DMXvOCQcaO09vhX8e64Kmz",-1840813548382119442,6706602965822855074,4749207314982805990,-3455025821623453829>(),
         (String)b.a<"sk2jft43pvoli","mSOSa3WqXoRB/GTmsqWuSl7Q5SDEUj6JbYuWJFHZLS3UzaaW/1U17giukA2aFJU1Si+OD6pYbX+VxHoePq9EYk9IRb6BBBMHQ+BCJ2sNIZ6Y8Q==",-2073326512259680918,-4783269437395156619,-2435713356032733657,-4908384172244919647>()
      )
   );
   private final Minecraft D = Minecraft.getInstance();
   private final com.yiyiaddon.e.j.b.a a = new com.yiyiaddon.e.j.b.a();
   private final com.yiyiaddon.e.j.i.b a = new com.yiyiaddon.e.j.i.b();
   private final com.yiyiaddon.e.j.h.b a;
   private final com.yiyiaddon.e.j.h.a a;
   private final c a;
   private final com.yiyiaddon.e.j.f.b a;
   private final com.yiyiaddon.e.j.j.b a;
   private final com.yiyiaddon.e.j.g.a a;
   private final com.yiyiaddon.e.j.j.a a;
   private final com.yiyiaddon.e.j.d.c a = new com.yiyiaddon.e.j.d.c(this);
   private final com.yiyiaddon.e.j.l.a a = new com.yiyiaddon.e.j.l.a(this);
   private final d o = new d();
   private final d p = new d();
   private final d q = new d();
   private String bg;
   private String mC;
   private static final DateTimeFormatter a = DateTimeFormatter.ofPattern(
      (String)b.a<"sjtxs7wztlj3q","XkkTCX840uxqcFELnC2aQAixQZgN8WsdW9tR68qhEBHx48ZhafAcOw1+YDd7yypbM0Y=",-5606281772002687849,6799904716564502243,-6316262078491447055,-7013753354115745574>()
   );
   private static final Identifier a = Identifier.fromNamespaceAndPath(
      (String)b.a<"sp04b1vmle5et","RGXLeacylU88NcvR/IO23OBh2nHRv/zd0/ngO3owuL8KcQhMcBexVXzSL/GmjA==",-174876002573002421,4388635645732633051,6841958276523997100,5125882777863856338>(),
      (String)b.a<"s29gmokb8tyegw","ATpWzb+jZEAVrQdTF4yB5RvgCntuyLUl82r6ST+jOisvfeuER3bfwhhxQ7qryL3GEaCVMTxHfaYcU3xS",-8659773569586661252,8697158605969923303,-5199147615443852688,-2011491039490206047>()
   );
   private static final int gb = 2;
   private static final double y = 0.04;
   private static final float bb = 2.0F;
   private boolean n;
   private static final int gc = 6;
   private static final int gd = 40;
   private static final int ge = 400;
   private static final int gf = 2400;
   private boolean bO;
   private int gg;
   private int gh;
   private boolean bP;
   private String mD;
   private long y;
   private static final long z = 5000L;

   public a() {
      super(
         (String)b.a<"s3cz9dmhn7d439","XN6qHBDFy6+uFvbtQlYVjpsQ3fUjm+Mocv1fhgHwucXYep02Gmqqfg==",3998512270173189326,7270419958382748158,-764379316194695946,-4327700679594361738>(),
         (String)b.a<"s2wiuwlx6a9ewk","oc7fcJ8VZD4vK7rGHXTQMcBHpHipoZig4cVNv8PtuUdP8L0q",-1934891433557063251,-1585985823197437748,-765522253584654704,7182927889503221946>(),
         (String)b.a<"slch43pl4ebc","MeJe1hn4twHiq87Fb7wNNhwlDEU5g2ZqbUgxJtBORUVPfWx/R3WCfAnzMI/0R3wD",3407680622560118117,-1158569483092342167,613172284995866143,3921661578397673075>(),
         (String)b.a<"s3v06ztb2ag53w","NG/XHn4elMFFAx15M/Ys4YyI6JQBZTOZHzknoUJ+wx1ZTzNPpZk4H7WVx0AzBpr3ZkJJ1XrbYHu/H+DKVi/0jGX3nhhLBkZhKfTYpWMiNtcgKztHwXO0f6sw0/jFcloMKfiYHhMADZu6feXy",-8865810940360200902,-1731196370622508683,2029560830505823302,-6886343715916264474>()
      );
      this.a = new com.yiyiaddon.e.j.h.b(this);
      this.a = new com.yiyiaddon.e.j.h.a(this);
      this.a = new c(this);
      this.a = new com.yiyiaddon.e.j.f.b(this);
      this.a = new com.yiyiaddon.e.j.j.b(this);
      this.a = new com.yiyiaddon.e.j.g.a();
      this.a = new com.yiyiaddon.e.j.j.a(this);
      this.a.n(true);
      this.a.b(1.0F);
      this.a.a(new com.yiyiaddon.e.f.b());
      this.S();
   }

   private void k(com.yiyiaddon.d.a.a var1) {
      if (!this.g()) {
         switch ((int)b.a<"s2hz88gpluisuj","5PE3wYWLjLvOIyoiCG5sF1pSJlEDdbyZidGMzAJBx54=",6998370345820579153,-2109591281294002964,434608560232979418,4666150223138676024>()) {
            case 1753920741:
               return;
            default:
               throw null;
         }
      } else {
         String var2 = var1.l();
         if (var2 != null) {
            switch ((int)b.a<"s3j10lwdngqjms","DyH6DxRE9QnPj+24beKZ2rElOqN7/YBGfwOc6vZAJEs=",-8230088841381181678,-6251654085486839073,-4396835368387974779,-1568683994027179893>()) {
               case -1244231419:
                  if (!var2.isBlank()) {
                     if (this.a.G(var2)) {
                        switch ((int)b.a<"s2bkswufatnhbj","CE2NeJMtVzBhUIL6TkLvXlTyfP0R4kzZlJ/sPDaDAbo=",4219694811362810880,-4737794194818696867,-3315442002671872456,9105042656633779658>()) {
                           case -1243042666:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.a.af(var2);
                     return;
                  } else {
                     switch ((int)b.a<"s25sdo0cmmce0p","Ta+wsRffcIcvjeGF5nYiqfN5KWHYRoT+ggd7G9Fpxbk=",-7823449570746385774,-2202273710851751754,8419601546865491316,4889085569583900279>()) {
                        case 1392408858:
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

   @Override
   public String a() {
      return (String)b.a<"s3bpn3satwpn7z","XwCybAFxidjqTKK3KiTozpcVPCwfw3XmDLshhF4gUlYaIABN5tjlXq2LtU4QHQ==",-7538559420943253176,-5041598308895263708,4000795078208520909,-4747935951041262562>();
   }

   @Override
   public String w() {
      return (String)b.a<"s1gv09ehj54bc8","7KH+YxdIrobDWLYcV80qxHrGMmFApaCyCEdoghhB",1814486194785275211,-2563496550516562759,5490491485493096199,1085928804646361558>();
   }

   @Override
   public int i() {
      return 40;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
      this.S();
      this.mC = this.z();
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      e.d(this);
   }

   @Override
   public String z() {
      if (this.D.level != null) {
         switch ((int)b.a<"s3u59h3zr0r7r7","EAXAfg1B8kSFzqTtytrFBA6Ln7zHs6cDbMggB0t6HXI=",353228290502585066,-7790562910469839766,5277251931629543360,7571964891881565393>()) {
            case 869324484:
               if (this.D.player != null) {
                  return com.yiyiaddon.i.g.c.gn();
               } else {
                  switch ((int)b.a<"s3sge2uwz9hbol","YkvBdawsVSnmupsLCaOrogjC3BGvFtj8SIhin8BJCGc=",9201380193101783292,-3089459544854776685,2137239991543874520,4990322578622478661>()) {
                     case 717836739:
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

   private void et() {
      if (Objects.equals(this.z(), this.mC)) {
         switch ((int)b.a<"s23mablsil3fwh","G6hGM/TGdK6zui0ylYkKphcpoIg0YV5QhcXrF4PVBQI=",-7353247466565321670,4497992537234495671,3484655445946470088,3716968297540227425>()) {
            case -1547115:
               return;
            default:
               throw null;
         }
      } else {
         e.e(this);
      }
   }

   public static String a(long var0) {
      if (var0 <= 0L) {
         switch ((int)b.a<"s1qi2z02p7u3cs","DBTr9ssruNZnLtyXfCeczP/17KrQEi3llRakm0PAupE=",4085892117037416160,-1847393639665959643,4843792307752000801,-2406643447805143217>()) {
            case -1149326813:
               return (String)b.a<"sfornsv11l1rp","jemxH0jMSF7znVG8q9+qQ8JJq0TB/Pbn4+r2SA==",396220849542488325,-1298840193335610311,-6643955711067199901,5703609369823525931>();
            default:
               throw null;
         }
      } else {
         return a.format(Instant.ofEpochMilli(var0).atZone(ZoneId.systemDefault()));
      }
   }

   public boolean bC() {
      if (this.z() != null) {
         switch ((int)b.a<"sbdq6yi8odtxc","RLDwrLOPCektmzuUAARiG4ErnG05s6ls1GdisT+zDSk=",5533808659328803526,4969653301419668544,-7197248779539578293,-496257355834455280>()) {
            case -2087035297:
               switch ((int)b.a<"s2qocu6dd3tof0","/cgriO8gg8lKqciFjGWI5fFKb9iGwNWz+LkKL4+Iqd8=",8516856296518269739,-3077494487422649111,4193690336761726570,2415331672826377203>()) {
                  case 1504175361:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s2j9qrd2kmrdo","3g1DaANPbTIf+21EtNzDL49SON7sKz2V032dtntdM+0=",-1643214282765943220,-8851300851371357572,7268346828204841681,4768614737786964805>()) {
            case 882785558:
               return false;
            default:
               throw null;
         }
      }
   }

   public String bF() {
      String var1 = this.z();
      if (var1 == null) {
         switch ((int)b.a<"s2v4cjifgt7arf","slb7r8+mniEpXdL7yrOEXrIfOhrXlGNh2cbWj8WMO+E=",-4911118100672256120,3472905676659029433,-6282016880567057042,748899141211830045>()) {
            case 1068429608:
               String var10000 = (String)b.a<"sfornsv11l1rp","jemxH0jMSF7znVG8q9+qQ8JJq0TB/Pbn4+r2SA==",396220849542488325,-1298840193335610311,-6643955711067199901,5703609369823525931>();
               switch ((int)b.a<"slucqealqughj","2nV5pByRs2bLLl+3BEaseo6CrrpOCdF+0Gj0a/+fNIA=",8366210727348261640,5800507393903323498,815916986117254165,-1568046755176437603>()) {
                  case -924221823:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s1uzshgnl22pti","7PPGBIXYLNtkMrxCgON7rNJE13WQaLd9If3I6mOZW/I=",-3168994221377852812,-3712675723850054833,3173244577165664729,-5177353840648616271>()) {
            case 233511672:
               return var1;
            default:
               throw null;
         }
      }
   }

   public String bG() {
      return com.yiyiaddon.e.j.e.a.al(com.yiyiaddon.i.g.c.fG());
   }

   public boolean bD() {
      if (!this.bF().isEmpty()) {
         switch ((int)b.a<"s2fmfzo5u9xuiq","ntsDeijJJZ4g327qJ26xDBBM06lMLuK66gZEpQNpsGM=",-8472610994882166851,1307121301645396223,1673395923099330221,8066750091802649500>()) {
            case -764504296:
               if (com.yiyiaddon.e.j.i.a.E(this.bF())) {
                  switch ((int)b.a<"s143ay6vwcv4e","Q4FhCvQVfbGtvOcnv+PJEYhqs0lOTjtpeIzcGtrEkIY=",557576771572991789,5734633579673106336,-5706350615102969484,-5949648073915553840>()) {
                     case -1831092068:
                        switch ((int)b.a<"s3h81q7adit7cb","ErBRTZbRs9n4R+EgkxLpz7bNZzFOdQHQAxHRYFwP2HU=",-7317352233091193428,1957553000839157613,-7933106621847331140,2605091130088541381>()) {
                           case -1944668371:
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

      switch ((int)b.a<"s2mibmr7hv909v","B0N7l6g8j6OHY5X0LrkvW5xgzPr4wI3/yXRxs+rncEI=",2408583797753616506,-3806321034311749229,-2207743577347912168,8197826148761893976>()) {
         case 1429543937:
            return false;
         default:
            throw null;
      }
   }

   public String bH() {
      String var1 = this.bF();
      if (var1.isEmpty()) {
         switch ((int)b.a<"s34k73izbmmlne","KPl9C39ZDvx4WAOxtdDM1F6gPZ/ColygM8Z+/JcHLdQ=",-8662939489034969036,-2200859344293497657,-2803515109557089570,2166613645719301595>()) {
            case -1202611191:
               String var10000 = (String)b.a<"sfornsv11l1rp","jemxH0jMSF7znVG8q9+qQ8JJq0TB/Pbn4+r2SA==",396220849542488325,-1298840193335610311,-6643955711067199901,5703609369823525931>();
               switch ((int)b.a<"sz6iko3q5pbf2","V4jDxJsg3t5piIlRb0PFeFgbDE4h8bfnJsFRwk0itRo=",4331981954200414230,8343291501966995141,-234341832554352856,406531406064492632>()) {
                  case -578125728:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = a(com.yiyiaddon.e.j.i.a.a(var1));
         switch ((int)b.a<"sjr262zshhkqs","32fqEtvdt14aCGk0kQmY+23luGCjzQLYdmOSA1lGGis=",-1456704039234976272,-1076295772358519219,-6851459693929631750,-3363025074086036881>()) {
            case 1419457655:
               return var2;
            default:
               throw null;
         }
      }
   }

   public List<com.yiyiaddon.e.j.e.a> aj() {
      return com.yiyiaddon.e.j.i.a.an();
   }

   public void eu() {
      String var1 = this.z();
      if (var1 == null) {
         switch ((int)b.a<"s1kguy4cylmx0j","SzsHiuzuwCojlGe13u2rC6h6hjnG9l2kwYpUG3+zLWo=",-4271399475671776184,-2290868745814123806,-8164752924986349707,7676231423412558253>()) {
            case 518365512:
               this.ad(
                  (String)b.a<"s2yvycgfvz3rfe","TyPcd0BwHEM00ZC/cPaLtGiKC+8xttbAFufDickCqv3frAV8eCvDsw6NV/JSvqSzWLj501laM3PxRGmiJhLj3t3ndgtCkdhZ1V9VMYVUDMA9Zj2fgUO0EP7FdwYqyRUn",-6217692774380628621,-5869881621362151427,-6624249951204827845,6136207870077666232>()
               );
               return;
            default:
               throw null;
         }
      } else {
         JsonObject var2 = new JsonObject();
         this.a.c(var2);
         if (com.yiyiaddon.e.j.i.a.a(var1, com.yiyiaddon.i.g.c.fG(), var2, this.a.c(), System.currentTimeMillis())) {
            switch ((int)b.a<"s9i24690m4zyb","/yWauS9+4jjZ+TaTMuykPab4n9Iy21VrzGoSPe64SGg=",-5570893324288130815,-3512264595709163165,4655070494366374206,8253075325154293970>()) {
               case -349077555:
                  this.K(this.bG() + this.a.a() + this.bH());
                  switch ((int)b.a<"s2fneavpuadi6f","jPVXvMD9XrkZc4s5CjitlD+1y9mXRdwdf8DAk9cy0Yw=",-4061069029570976008,-1626831301271417361,4979784532423864604,2641075026178627006>()) {
                     case 2004805558:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.b(
               (String)b.a<"s29da1277flg40","tm66VA0KxOpTSatU2v+/M5kocw+G0f37d0gxpZNGcB/rybPhS1Cbgj1XLp/mr78/eeXzpDYm2TifSlHhHymuCNoWqFgDLStMmiA=",-5776949788944290593,7369748414714560898,441815780635933397,5014360555851033294>()
            );
            switch ((int)b.a<"s1pq7vqlefg8px","kMaKCTwZgmsy3W1FmDxH/c3t/LYwD2idLNS4ZtzPnuw=",-5091121644021289052,1004275987332733051,-1900213770429595890,-4468367986453418674>()) {
               case -1273956939:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   public boolean a(com.yiyiaddon.e.j.e.a var1) {
      String var2 = this.z();
      if (var1 != null) {
         label31:
         switch ((int)b.a<"s4xwmy0xw4vn9","B0mLIHz7wRu/Uj9iazEbpKnwbxMu1qFyzyPh9Fhj4MY=",1111720120033890905,8753670543653886791,-8175070812340870016,6029295620818419867>()) {
            case -1037060843:
               if (var2 != null) {
                  if (!this.b(var1)) {
                     switch ((int)b.a<"s1ivdg1pwsdxe9","InMpFhxUJ3BaBIKXjg5A6z1bdR/OFx8J1bRNY1M6bh0=",-722730020359302564,3542236959483125685,6742597616251450483,-7929733582123495345>()) {
                        case -974935311:
                           this.ad(var1.m() + "");
                           return false;
                        default:
                           throw null;
                     }
                  }

                  JsonObject var3 = new JsonObject();
                  this.a.c(var3);
                  if (!com.yiyiaddon.e.j.i.a.a(var1.bS(), var1.bT(), var3, this.a.c(), System.currentTimeMillis())) {
                     switch ((int)b.a<"sa6chlbvzhbpl","vr5TDcp6KDz1C3kMbh9ExfzF34c1FNQjySRUp3BrvRo=",-8983721550584930515,2150885904812771304,5361290094350061364,1643723490629736985>()) {
                        case 1573107538:
                           this.b(var1.m() + "");
                           return false;
                        default:
                           throw null;
                     }
                  }

                  this.K(var1.m() + this.a.a());
                  return true;
               }

               switch ((int)b.a<"s3s7utc543eon7","Ga6HWHAqrHQKMrIHEMJFRUxPxmMPvY86AldQTovqXDM=",7893479878214933453,-2133197423145718057,-5857992667568851771,-1730853144453203851>()) {
                  case 906996368:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.ad(
         (String)b.a<"s1r96xp4n2ows4","k2yDsGgm1V7MKPeXhNSaaqXiP1oj4wgI6fVHMWm6C1uE/moqf8/SJT/xpVKlXJr6GKg4b5/V5vM6cmf2yRRjnYuq8Ea6VSO5UBFzXL6KlUY86W7aOe6NBen04uLSiabf",3470602829574722992,-3547449056273517274,8184394402296963776,5456260925378032380>()
      );
      return false;
   }

   public boolean b(com.yiyiaddon.e.j.e.a var1) {
      if (var1 != null) {
         switch ((int)b.a<"s11kvq5jxdq6p7","cEQggDFJFvUMjRPsnTqxHAonzZqRri/S1yh1xWZe6Ig=",-5736215505293595583,-2147382457807911388,-8556444206900176723,4216821520499831001>()) {
            case 1717739385:
               if (!this.bF().isEmpty()) {
                  switch ((int)b.a<"s2k2dip04dts62","1o+Fz3PmdvRsut4Bg0umcvpuNs968VCluaiR5jA3nXo=",-2660575868091593563,1840884701792491354,-7199281916031695187,-4014059340929663952>()) {
                     case -1708108674:
                        if (var1.bS().equals(this.bF())) {
                           switch ((int)b.a<"s3n90i4320agkr","zm9YI8CDZBAljfyK7kBSCO+ZzNLqe7FKedObTHhBLMA=",-835321021299776663,-2497204569787464688,-3077902709367384243,-7174161438263320697>()) {
                              case 461000519:
                                 switch ((int)b.a<"sm4o8lrvfhzuj","zGxmoEo70H6BwaJyou8i5kQIdrAynMP8j/7wnNo0hUs=",7188973719434840096,-5930142799832676270,-1652463004965280229,5476054626655599300>()) {
                                    case -1988594746:
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

      switch ((int)b.a<"scnjdsxmwlh8y","nUBz1g/CR74Sk6uiFC0hxmzOTehrU0IZF3FUHWZl3Ok=",-2930358241775295735,6113333394911700762,-4420089011260112661,-1373341292500544698>()) {
         case 1207493591:
            return false;
         default:
            throw null;
      }
   }

   public com.yiyiaddon.e.j.b.a a(com.yiyiaddon.e.j.e.a var1) {
      JsonObject var10000;
      if (var1 == null) {
         label22:
         switch ((int)b.a<"s3gvx9be4zyt5c","MzKxHMtp8jzaHUbkU3B1hg3XPOidthhNLdC7QZL3HvQ=",5896810180365493257,2000376503329251430,-4751671895387601618,4971211300731471561>()) {
            case -1092031769:
               var10000 = null;
               switch ((int)b.a<"s33i3pixsbveqg","aqhvd4W6GwIQXPnCPYlAisSBKg9ZEhkqDJz0ji6MzJY=",-5851680902410323164,3724951024943204411,-4175245929997764110,-112357648413124087>()) {
                  case -1412179293:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = com.yiyiaddon.e.j.i.a.f(var1.bS());
         switch ((int)b.a<"shacdb4g9d0k0","11d5JFFYXU8Aw2T8KtKFCMYN83cCli+vFVBbdncLKgw=",-5304462318821991952,1638809935430071966,-7336993387075365074,402446308187990325>()) {
            case 671229435:
               break;
            default:
               throw null;
         }
      }

      JsonObject var2 = var10000;
      if (var2 == null) {
         switch ((int)b.a<"sdiiwut6ecjh8","ye5Zx50eWecqEm4KLqH7zwkjm1CL6jiGSmYte2TVhPU=",8799809851912498264,-1301894240232123025,-3688395823720443386,1260133456915631009>()) {
            case -1677784499:
               return null;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.j.b.a var3 = new com.yiyiaddon.e.j.b.a();
         var3.d(var2);
         return var3;
      }
   }

   public Map<com.yiyiaddon.e.j.e.d, com.yiyiaddon.e.j.e.c> a(com.yiyiaddon.e.j.e.a var1) {
      JsonObject var10000;
      if (var1 == null) {
         label15:
         switch ((int)b.a<"s3v61pqh98pyf5","7K7Vuy/JQUi4Lh7U41MG4PAR//9e4azVLPSP6UZaLKE=",-7546242910239377789,-7724399772987490141,202298780530246958,-7494424203061059257>()) {
            case 837595117:
               var10000 = null;
               switch ((int)b.a<"s3dnrhd5djrdjz","5zvCuk2z3omFQyndwM9Px69zRdCaRkm/yVsMFjvkFxE=",-5714570744501502134,3877916062292948827,1307712351332455815,-8945955258031868463>()) {
                  case -190147264:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = com.yiyiaddon.e.j.i.a.e(var1.bS());
         switch ((int)b.a<"s1ilwb904nmxn6","mzhIpZSnrVsPC2W7ZykLB8MN5ZvqM+2Lr9vvzPFagEA=",-771037273826908877,-7368675966302839823,8787024398439480120,3344598034323183975>()) {
            case -1752672890:
               break;
            default:
               throw null;
         }
      }

      JsonObject var2 = var10000;
      return com.yiyiaddon.e.j.i.b.a(var2);
   }

   public boolean c(com.yiyiaddon.e.j.e.a var1) {
      String var2 = this.z();
      JsonObject var10000;
      if (var1 == null) {
         label48:
         switch ((int)b.a<"sk42se7ulrpx3","RxrVNSQQMhUTMwSlMdlawhujudC5CKwQemfjwU5EnNU=",2802237758757097988,-5188705354035160691,4595057186148791032,-5804824322372340112>()) {
            case 606099050:
               var10000 = null;
               switch ((int)b.a<"s25e1kyvsatv7j","RW/RO+YBXGrm65P4cBQDKukUD3qnDZD4Ydkx/H/SK4I=",4978159251210183212,6178608467469022284,-3539855798172243196,1575021070951182097>()) {
                  case 592358902:
                     break label48;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = com.yiyiaddon.e.j.i.a.f(var1.bS());
         switch ((int)b.a<"s2qkri6odvu3nb","EVPDsvSScXiIvLuOvLc/Mv2JK7PzyYNBlUIfhmJto6k=",2604815332528114895,-6612757040209615177,-5701211159614517722,1933313873294930124>()) {
            case 1848027524:
               break;
            default:
               throw null;
         }
      }

      JsonObject var3 = var10000;
      if (var1 != null) {
         switch ((int)b.a<"s3jr70plh030vp","r+fBMzyzke4b5yWmTb7Hao+TtYPKpnhzg5OI1KSH90E=",8199051428662771929,7479450499523475710,3709591372747737500,-8017589712635888416>()) {
            case -123026034:
               if (var3 != null) {
                  label41:
                  switch ((int)b.a<"s1zjhjsadrmyzl","OncgQ8Ub1GLXF8KCXtPT1HaYHdVeC/GR1EcUvx98VHA=",-117310041778154476,-4731856078296095227,-9066585989745909697,-7454854048206508191>()) {
                     case -1229242468:
                        if (var2 != null) {
                           if (!this.b(var1)) {
                              switch ((int)b.a<"s1b0zjgx542elq","s4zOwoWI6ssp/z9XrHU8/s2AXGzXydP/Ea/gGmWTm9I=",405500494437304998,943473531227271102,-8708938728117245679,3133545716734589258>()) {
                                 case -717524055:
                                    this.ad(var1.m() + "");
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.g()) {
                              switch ((int)b.a<"s1pyqitfgcffln","7er33vDJdXOJ7iXTF8iafYOSJiKsv1KB9kE59stDFzc=",3571769902790694059,-7689202402218497210,7335401520594059301,-7483612390175946692>()) {
                                 case -817589441:
                                    this.ad(
                                       (String)b.a<"s2m36itksriza1","+XGorj36XgpyzHeHOyg8kIoBTXVLQWCufkTp/nwfZzRjGZY/j/EltiQvTBKQKRC+B7pgUMatZf/qTnSasiUO/cnTyHRIlm+Jsjkploh2wEPrFID6l1OOmIe7T5EhKP/kjnDxOEO+ctSYqltNw9o=",9045614800722778736,-439704629935135862,-5731528875490602510,152692667171078246>()
                                    );
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           this.f(var3);
                           int var4 = this.a.a(com.yiyiaddon.e.j.i.a.e(var1.bS()));
                           this.K(var1.m() + var4 + a(var1.m()));
                           return true;
                        }

                        switch ((int)b.a<"sr3q2r1tqqab8","afdz/REyJNmt2JYroLQLdkX9/nIf9ld0YUarW/tVc2Y=",-1674177467403880140,-2801724857791192724,-5245030280741303120,-9160204340588693538>()) {
                           case -1160369289:
                              break label41;
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

      this.ad(
         (String)b.a<"s2bb1lbvejega8","v1pc5S2j+PxmTNKH+uVqeICas8weJGb0u3R6Q5i/HhZqSi1eFSwA7gmf+NSbhtHJLAWwjdzLouBSjOa2Acv69YmT5jdztwYC5kPifjdQKGkYQStMu5U=",-8092705144889849395,-2900625537201072261,-236476409714875074,1046775596757298050>()
      );
      return false;
   }

   public boolean d(com.yiyiaddon.e.j.e.a var1) {
      if (var1 == null) {
         switch ((int)b.a<"s2ss9dyimudftv","E98I6E0gTgIALG6/nbml0HqCVD0vKJpMAUygKs+BJTA=",-3924318838357139155,-7215484129064821117,455206990705313546,-7012898369827412915>()) {
            case 735665427:
               return false;
            default:
               throw null;
         }
      } else if (!com.yiyiaddon.e.j.i.a.F(var1.bS())) {
         switch ((int)b.a<"s2v3c02q9hl0fc","QiuQyRh+1zZnA3yyaNt3rn+5Tg9/NHR92K6LrZ1/lA0=",3838760838856210991,415588970860812018,7070754348741792176,-2525074406556210770>()) {
            case -564257792:
               this.b(var1.m() + "");
               return false;
            default:
               throw null;
         }
      } else {
         this.K(var1.m() + "");
         return true;
      }
   }

   private void f(JsonObject var1) {
      this.a.d(var1);
      this.S();
      this.L();
      if (this.g()) {
         switch ((int)b.a<"snjygmv1ghzhf","S0NESYOj4qpDSO+n5Qk4+HFhF2hEZkCuIzi8p/zdu8o=",-5552449474276714706,4634241020321924726,-4346880030144531036,6836560071969635341>()) {
            case 2106149623:
               this.ev();
               switch ((int)b.a<"s1vnmrbepmebk0","MmtTGf4Wj1ctn+QwfuLl89UTOSXjzOWudYolm7xcGR0=",-6112278721291326517,-4274803516789862190,-7394097583428735947,4475594560471265506>()) {
                  case 752198920:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.j.k.a(this);
   }

   public com.yiyiaddon.e.j.j.a a() {
      return this.a;
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.j.a.a());
   }

   @Override
   public List<String> f() {
      if (this.D.player != null) {
         switch ((int)b.a<"shzf2y7z4u5dz","plsSq5iWHaQYEWs5FeFYTQPq5GphLnAeYjMHmrkpZjQ=",-3067673109873694695,5355706341579079418,-1397835454451195862,-4697475125674246500>()) {
            case 258807388:
               if (this.D.level != null) {
                  switch ((int)b.a<"s33gd9qozeps6w","xE/SZw5FxmRb5CttlcdVOnpm4LDfbmqIn3J4T4HD8Jk=",823845218692082642,-8130121832458291105,7884491366065707110,-851922639953201426>()) {
                     case -203201683:
                        if (this.D.gameMode != null) {
                           this.eD();
                           ArrayList var1 = new ArrayList();
                           int var2 = 0;
                           if (!this.a.mF.isBlank()) {
                              label341:
                              switch ((int)b.a<"s2cj7jtwoluixw","aFV9aG5OaEazOfijP9H0EXOv1L6TzwxT2ykIOod3QoA=",1252848016679040140,1912551817882932916,-1248674174538872742,-2111709135148389>()) {
                                 case 183225257:
                                    var2++;
                                    switch ((int)b.a<"s14r0t8as7syk1","zogRVyEhzsqdnbEJhZp8fg0eBYD562sgpiPa5ARVLw8=",-4529367731690236118,-2111959318696385481,1338271969949454650,-2921748035471147487>()) {
                                       case 2093932566:
                                          break label341;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (!this.a.mG.isBlank()) {
                              label336:
                              switch ((int)b.a<"s18emqlvttnhb8","ow4z3QsYysxzMqoT8FIQyS7wnRQPI86Xw1xQpu19+g8=",6227107072086110555,53599618276524908,-1455302980693496412,-743434333531963668>()) {
                                 case 1267990377:
                                    var2++;
                                    switch ((int)b.a<"sv4w57ljq2jij","hwQOQgP7F+KUUl4njBsTNDzeEX7k2ulkU/nlXT6b0eA=",7710936427880394922,-695371885953123615,6508626195003216471,5493028854534018745>()) {
                                       case 1331514595:
                                          break label336;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (!this.a.mH.isBlank()) {
                              label331:
                              switch ((int)b.a<"s1m615le0otdrq","6gS2lkp1tAX62nPXZFn8cHtQBE7mGJvpGwE6qu8ocSs=",-6753131052769229185,-4789900820725962646,-1252097490861636842,-263847875543522153>()) {
                                 case -1302531642:
                                    var2++;
                                    switch ((int)b.a<"sipja7xj6x449","FRMdWqdsL/e63WV8RwzIRsMofAjyJb+6h2i9ajphtrc=",8819462856945516617,7622649762328380119,-5370361914513682653,-1679749883131136918>()) {
                                       case -1448738899:
                                          break label331;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var2 == 0) {
                              label326:
                              switch ((int)b.a<"s36vmf26sd1eop","3iFUlu21VmVNpBkX7LL7Jyoqf5ca+vXuDyNueEfh11I=",4136078974164242638,-4363487194385020884,-616522120040447422,6807706678303976697>()) {
                                 case 221919159:
                                    var1.add(
                                       (String)b.a<"s1oumr9bac9ocm","nJYlkk0dBUto8CHMV0BqIXRkaSV0KCO7CNpjLlndFYuz711hpnUPAqXpzRSv0NIK",-3114354944201317614,7945181963686586222,-5189289323724757124,9172876992316220499>()
                                    );
                                    switch ((int)b.a<"spz4uqxbix4be","Dqpy1HmN+RQ4hgJxL7CRVF0cj24jiiAk6uN/oG351XQ=",6481204668161523154,-2875715773589584361,4779508891639589060,411735283708062215>()) {
                                       case 477660069:
                                          break label326;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else if (var2 > 1) {
                              label322:
                              switch ((int)b.a<"sw2k6zy8xreyq","UY3LADiqNXYOTVlJ6vmsRUImip64xZyST3G8uQsEyrk=",-3770046323359137957,-3112538161319769232,4968714562384697550,1539845055219203645>()) {
                                 case 1090024958:
                                    var1.add(var2 + "");
                                    switch ((int)b.a<"s2nvvg69yevx83","CdOflnSzPcd4Lwu37om6uoOtS1S0iCWVXh2UHI2ycqs=",1616000923892128163,-3734600070703912699,-7013728092377284838,-193857679586113419>()) {
                                       case 2109294297:
                                          break label322;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var2 == 1) {
                              switch ((int)b.a<"s265x2l6wj4z71","3ukTXxo3Y1k8pWbXBoa/h3jUpoqjF+Gr09uHICXyMhs=",-3526980194066941491,6060259494672398553,-2458677297496246724,8898714304335447157>()) {
                                 case 384463477:
                                    if (this.al().isEmpty()) {
                                       label315:
                                       switch ((int)b.a<"s2yprv4aptzsm1","AASinJSrESHM2klmp1vFejjtFgZ9ekiapu7FbEhULyU=",7769070933201820328,7003028548712749194,-4425167711207738074,-9009785800424134273>()) {
                                          case -1895985354:
                                             var1.add(
                                                (String)b.a<"s2esbzel5a5ob7","JoE5xLK7ADekfQaVS0Aqa/IOpVzWQIsJVm/lBK9iA/4g2/3xCyWP8HNmquOQvliHVX46AdQv87kK9fYHFAQL3DiF2Mp2JQ==",-3103270458578222040,-4805898601808011087,7947941012836818895,-1969611167347663997>()
                                             );
                                             switch ((int)b.a<"s96vcx0ei1a0x","Z5uJEvw+7myWdiZUHs77V+zmVfXcqr15+vdOb+nsgV4=",77415517226889761,5184259817488485624,-7586748877803251498,3404126724484576063>()) {
                                                case 162925444:
                                                   break label315;
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

                           this.a(
                              var1,
                              com.yiyiaddon.e.j.e.d.MINERAL,
                              (String)b.a<"s3nhh47ctq6e87","UfO/uLIzNtGjprgxYKOxp8D1uNY7VTRIPsUeVkmq94kn8v9hCFRAnfcaZK1NBhKbzLo=",-1940382534308601463,-1703333519209726037,-7218230111469279546,-2125955283858092492>()
                           );
                           this.a(
                              var1,
                              com.yiyiaddon.e.j.e.d.FOOD,
                              (String)b.a<"scauzsjizqzcv","/S+jH214hH9CtktB0wFgASln4pGvhy3KL6jbKBYzjgy44zehCtGbgSlggZ4ulgoaPxk=",-5056279653807517089,7736978198703980185,-666603347516814943,-1942606331001821007>()
                           );
                           this.a(
                              var1,
                              com.yiyiaddon.e.j.e.d.AFK,
                              (String)b.a<"s3g2k3c5r59mxv","tkJxewF9vcYj2NrLtQPKZzD6kMRekghtG74N5aJQ9GPcLR857YEMWWSEBXPLdwRnbG4=",7552208741376297259,-838169336642955142,6226137651977059372,8138177252424903404>()
                           );
                           if (this.a.mI.trim().isEmpty()) {
                              label310:
                              switch ((int)b.a<"s1t8xlos18kxbh","PNiWWwU0jnUqe8z3anpCcrXeA9QHzR+V3UfcLdlicu4=",-5588538357965508247,3473652663217117560,-3712958740917758332,-5278468037502271405>()) {
                                 case 514479107:
                                    var1.add(
                                       (String)b.a<"s2v8oi3cbb9b2y","9LPEPN1IFBKduj7FRprY1Pb5Jey6XD1snNORvf5eahZ+BcKLF1N/cWCvHyJJskgeR8nQ63Ur3PI=",-6311178700152747809,-2732539800501583606,8704781518957613859,-6526421080001751241>()
                                    );
                                    switch ((int)b.a<"s2xrz9yepyk09b","Vbj1HGH1BRoI6kDJ5Y2v41jRnWOOi6H5a1nqstiP7n0=",-1729912625694792975,6764793817641689252,-3866129216242061848,-187197363213786869>()) {
                                       case -264674408:
                                          break label310;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a.mK.trim().isEmpty()) {
                              label305:
                              switch ((int)b.a<"s1ylcvianx1mxi","ud7Ss6CdVsq0Yxn9V5kUHziCtXFF5I9X79Zvl70sXSo=",-5163536152470950814,-2843891693369182475,4592948174727192239,5266210919801740123>()) {
                                 case 1623395609:
                                    var1.add(
                                       (String)b.a<"s2tar8kkdshuam","ol0Vu/qM4jz92irVQHhldRSCiMlCZXibm65ynrhCIWKRA/K1RHkpJvolVCFmyjg2cGC4PLia7pE=",-3966361664428236272,3342506593781555613,-788509466536665204,3251742379130158499>()
                                    );
                                    switch ((int)b.a<"spur44gu0ezmo","zEPyOPFwUrOv4zQhiGc920HVpYbqE6EYfy2dLn6PLeU=",-5405860291142258049,-4744878412288378514,-2326908504473794638,-7093670461308012828>()) {
                                       case 1973785206:
                                          break label305;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a.mL.trim().isEmpty()) {
                              label300:
                              switch ((int)b.a<"s1lz1vlvm7yb85","zqX7vBToyjWvZszuSTrJFKIpnUYErYuIldmWmubWrfU=",5439108502001895300,-2303506842311253967,5066501311507370236,-6112801927118177835>()) {
                                 case -518756833:
                                    var1.add(
                                       (String)b.a<"s1yn1ic3l4k91o","DWVYlkCMPsdJrWKzJcj/qjmcUPhhNw/0aS2cWy/VlIqQMp/lDn5mRxmG9nEaQVpmp4er+upmizs=",6362155968243900486,8492018911240864569,5455430483903996507,-8543688528488275886>()
                                    );
                                    switch ((int)b.a<"s2cfxn28xswb74","M0sN3GCen8wx9ccHB1hH4RDSw8Qtr/Dl20Z7DjNENqg=",-559812024559982781,2797360300979794458,-5464120585517605809,616654354637796541>()) {
                                       case 1056547263:
                                          break label300;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a.mM.trim().isEmpty()) {
                              label295:
                              switch ((int)b.a<"s3sv9qlbtcd8vt","ShoBaGgpGaTygy66wRnjOzW3JIFNCsQYlFpmcUWj06M=",252789274999795886,640312936797573512,2587602425492023767,5872552628251500856>()) {
                                 case -90715708:
                                    var1.add(
                                       (String)b.a<"s10rqcl719nynn","VYsIQkI1JZEwXwr0zgOXOaSZvsFsJLOg3NXlH6Cokd13ua64t9CgvGDfSc+fSlTxJH1LrfTmGT0=",5455080161484204621,-335610579357692699,-5782133415371863576,841870703506016992>()
                                    );
                                    switch ((int)b.a<"s1mma3ke1pgoef","lV/IzP8SKY7nUjCM8D5wszBkHF0jSPbxk77cqtieVtg=",-5402141665985463015,-8670935021454122758,-2936592330585036439,-6754941950616316764>()) {
                                       case -1185811871:
                                          break label295;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a.mN.trim().isEmpty()) {
                              label290:
                              switch ((int)b.a<"sqn1end33ajlp","/qay26Xij8D4Qeb/wBGgJ6EXN2S/nC7muYa1hfD08KA=",-1818492826871560527,5995812519572657358,2755993140394604502,-3778237713877586567>()) {
                                 case 768379944:
                                    var1.add(
                                       (String)b.a<"s3jfsi75a12cbm","N5DBH3R0xbpcW8RDRKTenvEUItfov0UmE+ggS8AV8J3nfO6kE/5Dd/mRNKiGUBlFGQgLtYZGq3Y=",-138217181627645995,-6205870866624036731,-2838231898299656674,7588599090773169482>()
                                    );
                                    switch ((int)b.a<"szcqmghgd3tl3","y1hZoXsUgyw+mn/Y+MoJVqQ9RSaheq/y35vmObGnsfg=",1096653153397318087,-7686469105474254221,608710707909643899,8730261554614680154>()) {
                                       case 1058181124:
                                          break label290;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           boolean var3 = false;
                           boolean var4 = false;
                           boolean var5 = false;
                           boolean var6 = false;
                           boolean var7 = false;
                           int var8 = 0;
                           int var9 = 0;
                           switch ((int)b.a<"s3d089s31hg1iu","goOtdOmx9uUhJBwNsnrthV9UtviRfETHEB+vEN4dOe8=",9082431175311833859,6824210247396908516,-8836456575630001715,7218182954049099379>()) {
                              case 2142371504:
                                 while (var9 < 36) {
                                    switch ((int)b.a<"s1flpv0jw0fbha","6pPljYzaE4HDKPrETFpuuigwshalvH239WQ0mR0bFIo=",6053711481005289650,-332709844697833991,-7934960448001520854,-6072821796567371685>()) {
                                       case 2063439338:
                                          ItemStack var10 = this.D.player.getInventory().getItem(var9);
                                          if (var10.isEmpty()) {
                                             label263:
                                             switch ((int)b.a<"s1zjy57wgxpbp0","DBhhYx+2mlpGJSwfh2ZF7Qgd8b71Ilewr4ETC67Ph0I=",1465693361762118627,5304285662003755213,1212000707514326920,-6713295913381723694>()) {
                                                case 1385624155:
                                                   switch ((int)b.a<"s1575t9mqn7cjw","5YbiqZgnAC8b3skRdXTzGd5AUoHvrgIc88C/2H9dZog=",9154664866730146962,6951997643379066911,3076576861583175018,1081220735470459648>()) {
                                                      case 2108195423:
                                                         break label263;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var11 = BuiltInRegistries.ITEM.getKey(var10.getItem()).toString();
                                             if (p(var10)) {
                                                label251:
                                                switch ((int)b.a<"s1514sommgtw64","TWwfxN9Yy4Jeu5LwMDJKkv9wTSF/4B451Xu/z+SSWmk=",9054511029262467785,-1902231985636455322,-207198489012078311,2006451346829015572>()) {
                                                   case 1394507935:
                                                      var3 = true;
                                                      if (this.a(var10, Enchantments.SILK_TOUCH)) {
                                                         switch ((int)b.a<"spo07jkbdlf9s","PC0CYt3HB2kI5mMF+i3RnDFR2VbeoIqANKvol9Txwps=",-4107694989411458182,-671590042371751916,6261539410096123382,-5016908202122979394>()) {
                                                            case -120519158:
                                                               var5 = true;
                                                               switch ((int)b.a<"s3c8ci2rcc70za","q68TFn4XCEgWP/7RW7fXUIOKJCb/hIKSMxgat7KKSRE=",2990971672187422410,6502463962614963640,5088376690253140132,1595131184715491304>()) {
                                                                  case 1650837571:
                                                                     break label251;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else if (this.a(var10, Enchantments.FORTUNE)) {
                                                         switch ((int)b.a<"s187y9pb1hjyty","cEdkoL1qCsfEq3Zm0kf+3WDbqOgfwu+QTZ/TXI3ymn8=",1446529416316352934,-7757317955839290304,2849508862225278262,8817129667405350707>()) {
                                                            case 1252872895:
                                                               var6 = true;
                                                               switch ((int)b.a<"s1k64qfkutld7o","VyGukoW5U0IIwFxhiFuxmLlVo1UMGW4IrOZTekq2+5Q=",6075529971167323678,2092762214986055103,7447653765499938959,-4054065724213738804>()) {
                                                                  case -1122922590:
                                                                     break label251;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         var7 = true;
                                                         switch ((int)b.a<"s1z1gjinnyjjyw","hOp585cNp0VHu917PI5+PwNbMkeQyoVo+8W0GHrnwng=",5656521768667308238,-7745314152125766874,7535823277723448075,5048398288564001455>()) {
                                                            case -390901148:
                                                               break label251;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (var11.endsWith(
                                                (String)b.a<"s3q40gzr629aeq","Um2/UO3ud9c7DSXtxlFHw0opMwNfZKH94DBI+m8izBUXS/2F6/g9UA==",-2972407193956405505,5996194044309805266,-2155195668147222543,-7276530909696121880>()
                                             )) {
                                                label247:
                                                switch ((int)b.a<"s8pc7vo6t7i2k","Nab350dofswfDyfu5pEVzHRFyzf4GbjaIcca0hVcZ4w=",6639648201992617179,9063913647206978565,5083718589662137612,-2506398453490721740>()) {
                                                   case 1003348466:
                                                      var4 = true;
                                                      switch ((int)b.a<"s2v3ss0sr4o0jt","/BNA0KtlczjwNdbBVTDNKVihiadgS0IJPgQNkKVsniQ=",-6757376228443411114,-3338434309306081037,7594136006241508715,3901332036072410336>()) {
                                                         case -1204302071:
                                                            break label247;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (this.a.bf.contains(var11)) {
                                                switch ((int)b.a<"s3mqi83gqlum9k","9qYKB6DwjACa0CWfS06X6BBAebcmJmEr0V9sQS1sEV0=",-1167216412794785698,-7477887522173084395,-7658809659391803385,3245846828576433179>()) {
                                                   case -106813315:
                                                      if (var10.has(DataComponents.FOOD)) {
                                                         label243:
                                                         switch ((int)b.a<"s26zpaa84pr5re","sxrbiKD3o6gC1tfgMEZFu9nalk9wc6re7ojktkF5aWQ=",-3613918150765327609,4088503660775159805,-7684608910361626562,7016885438362875099>()) {
                                                            case -1960043624:
                                                               var8 += var10.getCount();
                                                               switch ((int)b.a<"s1cbvfnmfmbdv9","H8vCC3Yg5R/TKoamQWPJiFUcxXVhODMDkQBcLjRA3JU=",3875504936219645928,-7067483114132355955,-6040937216987895499,-4942257373877472098>()) {
                                                                  case -154463674:
                                                                     break label243;
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

                                          var9++;
                                          switch ((int)b.a<"s2ednw8tsynop1","ZorMw9Q6PKGCumWsne+q4vM7bItoDnqNsHlibZrPb7w=",4350801897315838992,-2411360086477515314,-3412596980662617807,3894607749149463750>()) {
                                             case 670872748:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 ItemStack var12 = this.D.player.getOffhandItem();
                                 if (!var12.isEmpty()) {
                                    label233:
                                    switch ((int)b.a<"swgxdaz1le1xq","Tel0IJP4dtTQWrRkOihZzAco/mqL1yipWmzvVmrZEaw=",-7083111282632484853,7274389377936127409,628444525725596147,-902279338644290472>()) {
                                       case 872310947:
                                          String var13 = BuiltInRegistries.ITEM.getKey(var12.getItem()).toString();
                                          if (p(var12)) {
                                             label230:
                                             switch ((int)b.a<"s3g9r888wsoltd","nO3k7Z4L6W32fUG0s85W93quuRFAhs5uZ6DTlNH/INo=",-8931771119451292004,-3220784291409336098,-113716980835024380,461806803156361931>()) {
                                                case 1158466414:
                                                   var3 = true;
                                                   if (this.a(var12, Enchantments.SILK_TOUCH)) {
                                                      switch ((int)b.a<"s3rkitzgjy5m09","V1D7ThSy9+nQ0yZVaMHu4HK6LozftAF4E6RtF5qk0cY=",-4771717234524551535,-7740033271349221092,-8866265180141827097,2644574957215724740>()) {
                                                         case 1653124588:
                                                            var5 = true;
                                                            switch ((int)b.a<"s370wvmcfg26tf","kD/HytKKUFmsC6LLxcv0yQxizl3HsUNX56N5xVCVKu8=",-3894638904989301885,5457808169796655236,8403737167776615619,1557287257267426040>()) {
                                                               case -297292711:
                                                                  break label230;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else if (this.a(var12, Enchantments.FORTUNE)) {
                                                      switch ((int)b.a<"s1hx3b0ph1hxyh","TBqNBONF9vwdLvYL1c7sjVMFbYroHK1Y5WsarOp6aEk=",-562288032170375320,4963974862673280370,-5447325841882180073,2644439332997382574>()) {
                                                         case 542124764:
                                                            var6 = true;
                                                            switch ((int)b.a<"s2p1w3e6wxqy3d","LVWSgjZYTKf1YEVtT73kayR5Xcr/MRwILaWdZGpBFgM=",-5267784152453827714,8999379852398756979,2559984474212878790,5320517516864952932>()) {
                                                               case -1170267109:
                                                                  break label230;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var7 = true;
                                                      switch ((int)b.a<"s33kjutlywpvif","cEKT4dlgCa0+bvfTl9wMdcNXp44Z5saipMtDXG2srCM=",8074960125162119026,-8859733062674734295,3656708502871054196,-7581408249895158274>()) {
                                                         case -172564377:
                                                            break label230;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var13.endsWith(
                                             (String)b.a<"s3q40gzr629aeq","Um2/UO3ud9c7DSXtxlFHw0opMwNfZKH94DBI+m8izBUXS/2F6/g9UA==",-2972407193956405505,5996194044309805266,-2155195668147222543,-7276530909696121880>()
                                          )) {
                                             label217:
                                             switch ((int)b.a<"s20ez75nomhs26","Dv1xLWVPNt9+aeMichGON7pYklGc+Jj2X6Jg3ZMejos=",-5041877286945146405,-1678441360373471539,-1245969447038968009,8106125033453586597>()) {
                                                case 1672901514:
                                                   var4 = true;
                                                   switch ((int)b.a<"sx3yau0kmfcvl","tv3JQemMCxvD/0egHGEg/ZyjxpoC3f9Z4Bj8blaR+mM=",6059706628084725685,-5598258964142788734,5703717946140462942,3296056799825756968>()) {
                                                      case 473651994:
                                                         break label217;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.a.bf.contains(var13)) {
                                             switch ((int)b.a<"s27naenv0y9p0","wAYSSbPmhte1emXw4FLewVRM74DbTT+C8oqiGX2NeqU=",-9123091350799426119,5557491919780704523,-2995370913583204937,7163250183195546888>()) {
                                                case -2015461814:
                                                   if (var12.has(DataComponents.FOOD)) {
                                                      switch ((int)b.a<"swnsfdccbxz6d","vFtjXn02N4ISELcX7i7nP/tXQ5iXMyluS6JoYx89jSM=",-3671243692475289938,4822610444307569736,-2909758061056092556,3411373689902055830>()) {
                                                         case 1635331734:
                                                            var8 += var12.getCount();
                                                            switch ((int)b.a<"s2h476po7nj2o1","zA0BJI1PDhD/46ZH42QNxCDYBT0KG0ZL4OcIAxuTpG4=",5531828778537082740,-6506642670308383125,2369731905028109060,-2714414669007080171>()) {
                                                               case -911607055:
                                                                  break label233;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break label233;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (!var3) {
                                    label206:
                                    switch ((int)b.a<"s24zhmawhl2zxe","l0frl816i4WFNTFHDO/uRclTXzeO3QKqG6N+3JxLxDY=",3659610717280774073,6252385828933351078,-9038615410750652623,2043324699016759765>()) {
                                       case -368800631:
                                          var1.add(
                                             (String)b.a<"sm5uaq32ry6c3","F0kP+mcx+fjZWzZQuCZg7S0ut3/5XBWQ4SbnGi6IJpjJTYa0Wz5hlGWD1DgCgfDPnMwINg==",-2107732547024883429,-1191303590281280869,-297677232691125785,-8212978349742586977>()
                                          );
                                          switch ((int)b.a<"s1kla4ppxjc6n","X2n6A7NOY4aWvr/KcnTPsfiPf70ikuWyg8bVM1ikScg=",7881478432294296746,6759536498373337641,-4198172214607794369,-4678597537333656047>()) {
                                             case -1980554688:
                                                break label206;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (!var4) {
                                    label201:
                                    switch ((int)b.a<"s3f3mcvvyavj3n","zWO9fP/Z43EjCfe/bBbzeQv6Go+wpMTtjDBth0Gtv2E=",4644807835009859513,-2166746122436568071,8470168886042190425,-2529401654787658716>()) {
                                       case 730577138:
                                          var1.add(
                                             (String)b.a<"s2e81a9d804im0","8DY1ve4bCm4qy3LtcGRpJ0PlBErh6qzjzylN+CPUM6iitBcR1X+RNl9FAwxg2ISaDVxxxA==",-9111756168678905481,-1666186547328924105,2322894994789928767,-643383927811270146>()
                                          );
                                          switch ((int)b.a<"s1bzcvdcval1r0","JvXVLFSIUALKyvg49Ka9IvYqLacmJ43WnzJlHThqMSA=",-6944136241399367153,9082082992406382065,-8565086230663763535,-2676212279543028907>()) {
                                             case -1895056985:
                                                break label201;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var8 < this.a.gl) {
                                    label196:
                                    switch ((int)b.a<"s3ay07qdehoz2z","cD6JcHD3HzS+Jx69nHgOioMvUZusaysA7pm3EUBoKhA=",-3651049079553137064,-6622905127125927764,-3789093408521699493,5378339664380068259>()) {
                                       case 2082077816:
                                          var1.add("" + var8 + this.a.gl);
                                          switch ((int)b.a<"s2bwgdokgtmf5a","Tg3ifakOkr6uKTASCfndXqwnfi2VE5xrPgkTeTsvsIw=",-8972825686749368554,6828688773480939968,-1176100298062215914,4339891777754546096>()) {
                                             case 1620591332:
                                                break label196;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var3) {
                                    switch ((int)b.a<"s3gbmggepa37gs","4ffaDCu4hiXlkDtnoX4yLMSbyJ202QekNB+aPJVal1k=",-3391638987300622273,7731127029559828444,6692298746711200905,658539108584583155>()) {
                                       case 1610391638:
                                          if (this.a.a == com.yiyiaddon.e.j.e.b.SILK_TOUCH) {
                                             switch ((int)b.a<"s1o9zi3g1u9u31","UKxrRtFt2W2Klb2iWsmHMLI6c8kG2VOvNmOTer/khTI=",-5499331626900844072,3714971124484553825,-4891729352214140352,8758975919051417389>()) {
                                                case 1331231745:
                                                   if (!var5) {
                                                      switch ((int)b.a<"s933uhta6nyew","VuExyvGdXlkitqEjoRPR8KHFyjn3lc/0IWjWmJfPV+4=",5538567037192479183,6633723526259903235,-7955942431325060433,5815007391156454936>()) {
                                                         case -1786805660:
                                                            var1.add(
                                                               (String)b.a<"s33hjdngjjj8st","/iLYAZwRutJa5X0TiX8OSGPmmBQerJ5Gng2/Vdyini8kAAnFFTI+2iqsrWynfww721rng7PU5NsODaFaAW+W/ombvEaUkqCeDrqe/OaIWImp/cXZscwXTI+qI9QmLfaqsk4=",2923089617721264326,-8296221663680666732,8425341429842259764,5188199799525818143>()
                                                            );
                                                            switch ((int)b.a<"sdr5lidnq005u","5sJZjXkjOYidbnmlJJCCiQMCMofTQNoqrS7nU1AygEs=",-9024167376396296616,9125936092761004904,-3293015907978794296,6356027809120090096>()) {
                                                               case -703794308:
                                                                  return var1;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   return var1;
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             if (!var6) {
                                                switch ((int)b.a<"s36y3hzs9dfdzm","3IdSGhsSXKJ4FswU5yO3fE/g6xVxnb9eq98CmcqDz5g=",7964738701146080371,-1645249566802236708,1088382740811985823,-3217689427042051665>()) {
                                                   case -1602326511:
                                                      if (!var7) {
                                                         switch ((int)b.a<"s1nku28vnisj3q","lbsE9H6GtwZxpM2xEUjXCSjsGUdNnT3cUc0GDkdnSdQ=",7996811656015030093,-1297317575706319625,6145709510986669531,436611482039044272>()) {
                                                            case -1573027461:
                                                               var1.add(
                                                                  (String)b.a<"s1im7ypoa2c59z","AQX3WjXt5BwPJXgs6ynw4zdlzBUJjKpR0uJqbmzTShVukqBpwb5kzqBOLdJwlAoilwBSanM8Jajn7oIuWepBF4MsqOAP/PvR6SPAMrUkaiw3DNI4TIU1JXMK7fZ2zQ==",1132863009488297071,-7659296337147136306,-4120355134129733841,40886416879897271>()
                                                               );
                                                               switch ((int)b.a<"svbuq1vyv30a2","EWokpsSuJE+fEWiR3M3sGdQuaZpUffxF/S23zSN3zSg=",-2711979538563235789,1096273459909415468,-1711920371964578498,-5342260638797158839>()) {
                                                                  case -1489074403:
                                                                     return var1;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      return var1;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return var1;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s1fezyp06r2ty4","MrODBubVfxswZRgYHzfBLYCWYOKc4QKfQFh2Y7JkSwo=",4369905669323626544,1524486618295653068,441191678703694642,-1942752416719085446>()) {
                           case -1630679524:
                              return List.of();
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

      return List.of();
   }

   private void a(List<String> var1, com.yiyiaddon.e.j.e.d var2, String var3) {
      if (this.a.a(var2)) {
         switch ((int)b.a<"s13pkvy9mo3ifh","SbM1n77SyLzGwWmAE0p8Ry5hLy3+WRSnItvqXJ4ppas=",-4395785386229521300,714192126000197896,7661053062638635821,4067120112491056083>()) {
            case -8975835:
               return;
            default:
               throw null;
         }
      } else {
         var1.add(var3);
      }
   }

   @Override
   protected void m() {
      if (this.D.player != null) {
         switch ((int)b.a<"s1t9umvx44ln6h","8NCEdtOYCg1NV8HOa2RNiA4SBT7fhtNjqof+/75xb9c=",5345024320928847228,-7848396264255818720,4775910965276255485,-4760338403792799373>()) {
            case -1102753193:
               if (this.D.level != null) {
                  label20:
                  switch ((int)b.a<"sdrdav80f9u91","XBToHdkV1okj8BdkO93tW+KbkxajWGndssrCGoz6wA0=",9097606731920333403,1726234767093222908,-5469454008577521235,-5893584661730981014>()) {
                     case -402952179:
                        if (this.D.gameMode != null) {
                           this.eC();
                           this.ev();
                           this.a.f();
                           this.a.f();
                           this.a.f();
                           com.yiyiaddon.e.j.c.a.a().eG();
                           this.a.f();
                           this.ey();
                           this.n = false;
                           this.a.fd();
                           com.yiyiaddon.l.g.a.e.a().z(true);
                           l.a(
                              (String)b.a<"s3cz9dmhn7d439","XN6qHBDFy6+uFvbtQlYVjpsQ3fUjm+Mocv1fhgHwucXYep02Gmqqfg==",3998512270173189326,7270419958382748158,-764379316194695946,-4327700679594361738>(),
                              this.a::render
                           );
                           l.a(
                              (String)b.a<"sffwp7tjxiet9","5BXa0iMEiLU3zK3Md9+vlbPjW2KT27DFQdwmqRsEhVD3tbaKTDP3x78uly8zI2DNcxQf1g==",-2145720146438974270,4402151203984070223,5340063181538659317,-1284088333499484692>(),
                              this.a::render
                           );
                           this.ew();
                           this.ao();
                           return;
                        }

                        switch ((int)b.a<"s3dbxlnvfbjnoh","2nieb8P7+X06Ydbs14xLlBHYVSIz9XgR72JMYd0/6qg=",-1256497683786826769,-2613164959061117317,5546521144385787022,-491758296233911012>()) {
                           case 460462225:
                              break label20;
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

      this.o(
         (String)b.a<"s2ucg7rf7bvvno","1GlTje5D7pTzZ079kOjmUCC91iZREp/NEc6PrD8soDq/2wFfy8fQ6FEcNkc47KuP9roEy8cGMde52w==",6521604369449387740,4510605713137268349,-1747659756699458615,-3006211198857323920>()
      );
      this.D
         .execute(
            () -> e.a(
               (String)b.a<"s3cz9dmhn7d439","XN6qHBDFy6+uFvbtQlYVjpsQ3fUjm+Mocv1fhgHwucXYep02Gmqqfg==",3998512270173189326,7270419958382748158,-764379316194695946,-4327700679594361738>(),
               false
            )
         );
   }

   private void ev() {
      this.a
         .a(
            this.a.cb,
            this.a.ci,
            this.a.gv,
            this.a.bW,
            this.a.bY,
            this.a.gw,
            this.a.cj,
            this.a.bZ,
            this.a.ca,
            this.a.cl,
            this.a.cm,
            this.a.cn,
            this.a.co,
            this.a.cp,
            this.a.cq,
            this.a.gx,
            this.a.gy,
            this.a.gz,
            this.a.gu,
            this.a.cr,
            this.a.cs,
            this.a.gA,
            this.a.ct
         );
   }

   @Override
   protected void n() {
      com.yiyiaddon.e.j.c.a.a().a(this.D, true);
      this.a.f();
      this.ey();
      this.n = false;
      this.a.ag();
      this.a.cD();
      this.a.eK();
      this.a.f();
      this.a.f();
      this.a.fe();
      this.a.fi();
      com.yiyiaddon.l.g.a.e.a().z(false);
      this.ex();
      l.l(
         (String)b.a<"s3cz9dmhn7d439","XN6qHBDFy6+uFvbtQlYVjpsQ3fUjm+Mocv1fhgHwucXYep02Gmqqfg==",3998512270173189326,7270419958382748158,-764379316194695946,-4327700679594361738>()
      );
      l.l(
         (String)b.a<"sffwp7tjxiet9","5BXa0iMEiLU3zK3Md9+vlbPjW2KT27DFQdwmqRsEhVD3tbaKTDP3x78uly8zI2DNcxQf1g==",-2145720146438974270,4402151203984070223,5340063181538659317,-1284088333499484692>()
      );
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.SCREEN_OPEN, com.yiyiaddon.d.a.c.DISCONNECT, com.yiyiaddon.d.a.c.CLIENT_COMMAND);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)b.a<"s3pn4narzuaz41","ajeF9ALKBwLukxOYlXCbRywjt9PUXWbfPUbJbe9EnqM=",-7666257419465684588,212554726087009305,-6227063902178207073,206252725352607635>()) {
            case -960615698:
               return;
            default:
               throw null;
         }
      } else {
         switch (var1.a()) {
            case DISCONNECT:
               this.a.cy();
               this.bg = null;
               if (this.g()) {
                  switch ((int)b.a<"s1xt5m8penf2ko","FSP6ksn9o5lsrjEotK4EPGAFNKl6V2LXvH+WsGCPq98=",-4947075198464823539,8202751721344040708,-3087002159153545864,-1885369099443520068>()) {
                     case -1099639543:
                        e.a(
                           (String)b.a<"s3cz9dmhn7d439","XN6qHBDFy6+uFvbtQlYVjpsQ3fUjm+Mocv1fhgHwucXYep02Gmqqfg==",3998512270173189326,7270419958382748158,-764379316194695946,-4327700679594361738>(),
                           false
                        );
                        switch ((int)b.a<"s3i9agpx2ta8tq","tUOdyymv5TFtqE7Yipm1jpe++m+E6nWSmkKdtJWDhB0=",7151178434702269745,-7656985501117830024,-6342349343243038952,-6535833111978787591>()) {
                           case 1158723115:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case SCREEN_OPEN:
               this.i(var1);
               switch ((int)b.a<"s7emipr6b3ers","/Tz3omh0AIhFCdrxfLb9dzdqkDBOttXy0YnkqJD6Jis=",557926633702608945,5871309350406541098,3547264729847800200,4158805772331107962>()) {
                  case 865106075:
                     return;
                  default:
                     throw null;
               }
            case CLIENT_COMMAND:
               this.k(var1);
               switch ((int)b.a<"s3g37uv0n8m6lk","ZIwBEiZDB9KOWPgWRoN8XCRrhpEHp3jqgJj+CBeljh8=",1890668489958029846,-7364042499483155992,919028558247084779,-503980038906671984>()) {
                  case -486149493:
                     break;
                  default:
                     throw null;
               }
         }
      }
   }

   private void i(com.yiyiaddon.d.a.a var1) {
      if (this.D.player != null) {
         switch ((int)b.a<"s1kciqla8wmmga","dwD/lutt8Gm3APhkK66rYBkVEKnnbWv2ldA7U1R3AQ4=",-693596961979494171,-3889957953769072660,-4459673511812477202,4078183035560005326>()) {
            case -685760205:
               if (this.g()) {
                  String var2 = var1.l();
                  if (InventoryScreen.class.getName().equals(var2)) {
                     switch ((int)b.a<"s2iislpurnqlox","0ceTqe+/gYDYO1NeohtNHAAoNytgSXvgQhvuJUVwK6s=",-4595832946696301853,-3453613470547898755,-3253856239292317845,-1791979678865788280>()) {
                        case 1245524099:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (m(var2)) {
                        switch ((int)b.a<"s1mvatzv9t6cng","+LM5DqtM6KXhTbBwpklS+TTWLZmBfeMosBc7r8UPRRE=",-7306859044694534745,2986216510426262313,-5644385659937187551,8131594161118416218>()) {
                           case 645117664:
                              var1.i();
                              switch ((int)b.a<"s19k9nlnqazl3m","BT/6pskA8gVN9+oi9t+Y8eQ5cu9b4kjHq+jcrjNJOPs=",7968745530601004467,-1921385913217816107,-8378851455129330760,-3560351990114002561>()) {
                                 case 1689714780:
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
               } else {
                  switch ((int)b.a<"s20tgijjtsj8mj","72Seeep4pyZA3PfYT06UQ1Wv6R+gkMLRlyZLRMtB99Q=",9002475699994923911,-952442133824003234,8441779609915727392,1413668680964511977>()) {
                     case 446309656:
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

   private static boolean m(String var0) {
      if (var0 != null && !var0.isBlank()) {
         try {
            return AbstractContainerScreen.class.isAssignableFrom(Class.forName(var0));
         } catch (Throwable var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void b(Minecraft var1) {
      if (var1.player != null) {
         switch ((int)b.a<"sal2477icdtx1","LppY3qUfx7ab+Zjja4AKX5bwKWMdElkQLUGwnWJzFmE=",-429020585728734950,2543823316939578467,6984508818949480528,2127376317259515909>()) {
            case 1939487887:
               if (var1.level != null) {
                  if (this.bE()) {
                     switch ((int)b.a<"s3r5ua2h32salf","7UV8ItPvhVZfJShSrJdLpP23uK64gkTPt4lDjwrt6BQ=",-3844405632204270725,-3876645131737470938,2340684198072451566,-3634861957843656520>()) {
                        case 5884912:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     com.yiyiaddon.e.j.l.a var10000;
                     boolean var10002;
                     label86: {
                        com.yiyiaddon.e.j.c.a.a().a(var1, this);
                        var10000 = this.a;
                        if (this.a.a() == com.yiyiaddon.e.j.d.a.MINING) {
                           switch ((int)b.a<"s3j4tfilq0ve7w","jfN5Fv+agSGVMKkJ9BI4stkD8/S+/0zZ2d7L0jSHWgk=",-2903489086857118900,6553472637995123007,5184019749900979662,-1951802219638465104>()) {
                              case 136622942:
                                 if (!this.a.ca()) {
                                    switch ((int)b.a<"sp245uh2i4tqz","4w0A+ljKbXTpU0NT8p5qABmsjieCwHixcezE7ch0jZ0=",3609713681730783990,1467987334064336408,-9144926799142617114,-4583283468300700804>()) {
                                       case 803316731:
                                          if (!this.a.cb()) {
                                             switch ((int)b.a<"s2qs3u5yi295zl","q00pYaNkLwtRazsL7oYiCkfHmfWVGYmee1+Fzy6A7qA=",4305371482235900237,1802489851584947739,8138438542284647286,7318394692093539131>()) {
                                                case -1587609055:
                                                   var10002 = true;
                                                   switch ((int)b.a<"sbmk9y91xlp39","0228AwkXcZylUXg/Z4r412BGIpmjLonokgitZKXKCCU=",-3612270613478149512,-8643217251589029287,-8533506845449371974,-6121196839309102504>()) {
                                                      case -1239676359:
                                                         break label86;
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

                        var10002 = false;
                        switch ((int)b.a<"s3jx1iuzy4jv72","2YtABmmnmUHlESZaRf6Ri1UX8maR2dgWf1p0UEiOz1w=",5487584991479187297,7506051087234696929,381023971456073258,-4315287510879084956>()) {
                           case 2073901865:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.a(this, var10002);
                     if (this.a.cg) {
                        switch ((int)b.a<"s1geszcmvkz5b1","9YddUlxWpatDMdi60ClB36aJ9gQH12Ld8op0Cn2vwyk=",-2779425471675805988,-4748050276588535817,6077509532494425918,6647144289539482452>()) {
                           case 1178941467:
                              if (this.a.a() == com.yiyiaddon.e.j.d.a.MINING) {
                                 switch ((int)b.a<"s22ekmdb10lqvj","pK3+dgvGPkhRDIM8SFODUWLAYh1eKnhXiLx7QCl0tsw=",-1488081443123726891,-4730255489054304680,8314068447843308217,-6115108603483721563>()) {
                                    case -1994601124:
                                       if (!this.a.isActive()) {
                                          switch ((int)b.a<"s1ja7drbmcpwsn","Q2LVjI8JyIBXk5dNQmGo3iHSBT/7TWamMFsor1yES2E=",2230151541920809216,-6671054694607131930,-6003357090566254281,-6567512529324149752>()) {
                                             case -1259184349:
                                                if (var1.player.tickCount % 40 == 0) {
                                                   label57:
                                                   switch ((int)b.a<"s1w8wi9p89k5ow","8z+VhLisQUhkyp/tPQODS74MoKP1Y+ieXVBWnH1Fnx0=",4818074761116424245,3826837084331235402,-8063316862251617962,-2107249673698084947>()) {
                                                      case -468202613:
                                                         this.k(this.bF());
                                                         switch ((int)b.a<"sfetmq1b8ctoi","56RixxpR5fmBsHJ9xpRs8OfVUCnZPM9jGM+iM//aRHc=",-1385445139340382442,-562912966922679006,-494106973703982598,-1375973609576307072>()) {
                                                            case 284932854:
                                                               break label57;
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
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.a.ae();
                     if (!this.a.cc()) {
                        label52:
                        switch ((int)b.a<"s28ubvnezdgo0w","Fxk1/sXjBMiJP7h1LA9ZyXhVD9S4Yp36tPibpalQqZs=",7203352088454705948,-4400260974244387271,-1609900169407410798,6256816456706552645>()) {
                           case -1441625295:
                              this.a.b(this.a.be, this.a.bg);
                              switch ((int)b.a<"s36ocs1ssuybme","pDFqbFh8aT2QqAw81myL6VQymJucD6JTYLfgGr6BoGE=",7356518164146043796,6110934265599870179,-6194655678743967314,1230988662928351896>()) {
                                 case -1960317774:
                                    break label52;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.ew();
                     return;
                  }
               } else {
                  switch ((int)b.a<"s1a5v4ah2qqbnr","zZQGKwovJmcKVA9ihTD8JTI2PsBTp2sC09DiPAojO9I=",-2551989173426528423,953858215915576360,6815456646651249058,-1224742195187034334>()) {
                     case -1879093406:
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

   private void ew() {
      if (this.D.player == null) {
         switch ((int)b.a<"s1h6djw88xggrj","5bJimPYxlM2bK2E0gFsgxgRhpFiqw4VJK4cn2xNdB0M=",956469776544019053,4759028929484283962,8231694553623575616,-6055291568173235630>()) {
            case -402156807:
               return;
            default:
               throw null;
         }
      } else {
         AttributeInstance var1 = this.D.player.getAttribute(Attributes.MOVEMENT_SPEED);
         if (var1 != null) {
            switch ((int)b.a<"s32nwvztl660vo","9wpumx1FDQyRTkLcrWMDJCRShxdrMCCmsBrtDJ8A8Rs=",412852070401144723,3215300359232919999,3631910047986297322,-4694751318741396884>()) {
               case -1801034059:
                  if (var1.getModifier(a) == null) {
                     var1.addTransientModifier(new AttributeModifier(a, 0.04, Operation.ADD_VALUE));
                     return;
                  } else {
                     switch ((int)b.a<"s1qvo22tfqxtnx","arH2cafEsHcMcdekL8ld0zY6JLkaI8mer4SObTdyVdk=",773367031527894016,-5520224592542158773,-2195606043739986468,5304708710433871319>()) {
                        case 1392012309:
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

   private void ex() {
      if (this.D.player == null) {
         switch ((int)b.a<"s3k43m1ovnqoki","Iwq+cFjlmFKq19fSX6z+c82TLY1GjkeSmsrG39ebQ0M=",5185431167106954927,-6609449853758656075,8772122913481938243,-4080900284952532707>()) {
            case -1946050942:
               return;
            default:
               throw null;
         }
      } else {
         AttributeInstance var1 = this.D.player.getAttribute(Attributes.MOVEMENT_SPEED);
         if (var1 == null) {
            switch ((int)b.a<"s2uamh0oow1b9m","NNsRiqRMMm1+Chd5eO473ZV26amXGeM+NwW2MsycUko=",-723281670732449150,-2743749935634052356,-4376024063975836479,4033456149511819415>()) {
               case 1571703764:
                  return;
               default:
                  throw null;
            }
         } else {
            var1.removeModifier(a);
         }
      }
   }

   private boolean bE() {
      if (!this.n) {
         switch ((int)b.a<"s2stvc6a52ryw","TL2USKWw9CW1vUqjwgiW3EIPB2SDbnIM5c3pzZ8qdZo=",9025972086292617324,-8556383431459651458,-2506564679611675360,-7643525467954574075>()) {
            case -1843970423:
               if (this.a.bT) {
                  if (this.D.player != null) {
                     switch ((int)b.a<"s1iqhrmzwjbl7i","McKFkbq1PZbgv6zr2fSp56YrSHocu9pTNGNfoGPNg70=",-8377609504000117027,6551513779351041244,-8130256749377416775,2015740921216191685>()) {
                        case 1406259420:
                           if (this.D.player.connection != null) {
                              if (this.D.player.isDeadOrDying()) {
                                 switch ((int)b.a<"s2m065ra6mgijm","NEXJiJWyBCUEKocIr3cigdmFAFZe75Ad/cT3NDfcFd8=",7150119026162766330,5704564127907832113,9017098967055495915,8732906990618433515>()) {
                                    case -1809158282:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              float var1 = this.D.player.getHealth();
                              if (var1 > this.a.gn * 2.0F) {
                                 switch ((int)b.a<"s3a8x0f1437lp0","Kn0YRTPhGckHJJPUNuDUHTsMd47e/o2HyNJL1yTsoEc=",-529753825418131050,-6422151732208357978,-8223766935026593941,8277308822042406306>()) {
                                    case 398630214:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              this.n = true;
                              this.K(
                                 (String)b.a<"s2qv8bzc1ygv2y","OIujE+wrZkias5quBEvECH1ENO6oMbcoDuykVaHns5DH1U1QbxNi7+5CD6Kxk/tqi8ct7yaOiTTMePcNT9Gq/4TtOzaRvGLWEBaCN7YB1ZV2q//t",-3481488896726604217,8278070732682013024,6153243932011355248,8995923607552075658>()
                              );
                              com.yiyiaddon.e.a.c.a.a(
                                 (String)b.a<"s2wiuwlx6a9ewk","oc7fcJ8VZD4vK7rGHXTQMcBHpHipoZig4cVNv8PtuUdP8L0q",-1934891433557063251,-1585985823197437748,-765522253584654704,7182927889503221946>(),
                                 a(var1) + ""
                              );
                              return true;
                           }

                           switch ((int)b.a<"s1h5w3vjiu19fn","SeZl8wsozQRBwfPHHi2uJYwubdaVAdPSRaCc5mIK91o=",-2998420530778804446,1312474019186962906,-6972230305684731095,6003010290605763953>()) {
                              case -1087298803:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return false;
               } else {
                  switch ((int)b.a<"spqwl6ny04v6k","cvV3gP9Y4VWxYu0MyqzdWY/ZIPNYkW1Kn/0o5jJ6w0w=",-8089921090436008877,7052850786238080183,-6256272045693795151,-6598236541136739970>()) {
                     case -1625096445:
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

   private static String a(float var0) {
      float var1 = var0 / 2.0F;
      int var2 = (int)var1;
      if (var1 == var2) {
         switch ((int)b.a<"slhofzyij1iah","+dIPxdlUqdP93K0tkv/v7BRi9niqN0GdWfDuGWRSE6Q=",8676841552849120905,4410394606466781960,3003869916124787246,-2332578809405825317>()) {
            case 2021837296:
               String var10000 = var2 + "";
               switch ((int)b.a<"s19vukystfqfdy","GvBKA4PnMi2k4o7dQF6uPUaCwyaXg3gdrJYt3GXrvmI=",6668136035817607215,-6957329402244541374,867609468378038226,575224236058643617>()) {
                  case 1575961672:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var3 = String.format(
            Locale.ROOT,
            (String)b.a<"s21gutwjsdzpc3","uXfYYBNniI1QsqsE0jtfjtjpdNp3Y5MphPUvK8qvQKtu28Yku+GHgg==",-4000791833561039341,8543373527426505115,6250590761210642196,3656502158657592730>(),
            var1
         );
         switch ((int)b.a<"swytnoyg19gjj","6bAvo3liwLY05V2533JzGiq/Y2gm68yr9bv7YUkwAtg=",-5276012656838269133,4281078669696347040,-1217257862044201484,-3707784227534117123>()) {
            case 2064135523:
               return var3;
            default:
               throw null;
         }
      }
   }

   public List<Block> ak() {
      List var1 = this.al();
      if (this.bO) {
         switch ((int)b.a<"s9ehw60mrguoj","e5WzLvqU1/2anAdJUdR/ky0rteTC+6AqTTyGgntMDBA=",-1079863917827462652,6434315896460009104,-6682442957021335166,-4809331866139709605>()) {
            case 1034917758:
               if (!var1.contains(Blocks.SPAWNER)) {
                  ArrayList var2 = new ArrayList(var1.size() + 1);
                  var2.addAll(var1);
                  var2.add(Blocks.SPAWNER);
                  return var2;
               } else {
                  switch ((int)b.a<"s3s1z606b70hg8","FtKmtCZLg9n4G0ELPHk0lbNaClQcfRMkgCClS1ascMs=",514883661712334609,34644236469320550,6037757216979840996,-7487196261287538972>()) {
                     case -1942334228:
                        return var1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   public boolean bF() {
      if (this.a.cg) {
         switch ((int)b.a<"s2ttoxrn6v4rrc","fCSXulC/X3HJ8CH8QNW6nEtAWFnc9uPXoNh4mXNB7Cc=",3276522279446531942,7140426021117422880,2251845695616828323,7872293603048253110>()) {
            case -1441759186:
               if (this.D.player != null) {
                  label79:
                  switch ((int)b.a<"s2v64ta6ryj3ut","DOWoa9FizfbsRiwFVC7lUYDT1lR40Pr5vJ6YqJJdbqY=",-7706203772658821348,7480091982427446510,8227648158158723519,3726324036585403104>()) {
                     case -1377910003:
                        if (this.D.level != null) {
                           int var2 = this.D.player.tickCount;
                           if (this.bO) {
                              switch ((int)b.a<"s11x6bm607k4t","+NIvIv/YCrqsoef1ouOjvB1BsXf3V+YMGI+kLTtCIVE=",2584439182461581362,4214443135351530876,2925914050115211539,-4480675752244522341>()) {
                                 case -1321116345:
                                    this.gg += 40;
                                    if (this.j(6)) {
                                       label63:
                                       switch ((int)b.a<"s187yhj34mild","pNC4W4Qn5ZDwwW+2uMg8kq4dSx3+qeWTvi9+i9Hx94c=",-7240017286105982299,-5216478112164256417,241952142683131815,1318084348246677934>()) {
                                          case -696277081:
                                             if (this.gg <= 400) {
                                                return false;
                                             }

                                             switch ((int)b.a<"s32kia78lqvot0","1jQGvONihd3tYK/+EJ35MVgxydHMts1UqSoPidhaB0o=",769114166072898108,7186442307860575715,5615570020040593842,-4093823931286445192>()) {
                                                case 667573437:
                                                   break label63;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    boolean var10001;
                                    if (this.gg > 400) {
                                       label54:
                                       switch ((int)b.a<"s2yjj0z9qi6ntx","TnoO/h4yFU/USXDsT6eSiQvat754f9aWx4A9eWUU0G8=",2198561712669069468,-414183422025254651,7240780289775218289,6499147448069700203>()) {
                                          case -1492415192:
                                             var10001 = true;
                                             switch ((int)b.a<"seri79vig5o2y","e51beO8BQGA5ofo8JB/nAQCH23V2+vmGZkGbXt9vzq0=",7842866292222205155,-6494953112958689345,4380196224791999325,8740066158722298077>()) {
                                                case 1225570487:
                                                   break label54;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10001 = false;
                                       switch ((int)b.a<"sfk3nl74fyu3","G5m4vhOebggdNzckX97BBS1mQRB3Kd4VCk9QVpcuvNU=",6472876601398941817,-2062537403214154244,4989866326649255322,-9039385544248119079>()) {
                                          case -1818843091:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.bP = var10001;
                                    if (this.bP) {
                                       label49:
                                       switch ((int)b.a<"s37hl2747beemv","iQh+A762J516XXM0yBkK3/DaucbK/J+IUCvbhNL/7mE=",-2392754519426393024,1462883406638197426,7140227809442059437,8596203475295767716>()) {
                                          case 1159843073:
                                             this.gh = var2 + 2400;
                                             switch ((int)b.a<"s3w07ryo577lio","J+6H/2ee5SfcYiMcRH1rQfUn+9hypQliZmZ+PR+OFnY=",4742589377192794984,1770172368534358686,-4627181588892003683,-1685265768988877400>()) {
                                                case -836455862:
                                                   break label49;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.bO = false;
                                    this.gg = 0;
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           if (var2 < this.gh) {
                              switch ((int)b.a<"s1lw2vu5olqjx7","wlS63qLoYs2JSG1VOXMjE23G1nOHdWoS2iPCltaRW1Q=",-4040557405051452050,5768582434164089933,-1551137549433117007,5361879649261073719>()) {
                                 case 1353762917:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           if (!this.j(6)) {
                              switch ((int)b.a<"s1web21816hitr","UkrAgrnCi68hH4UXD3m836g02M+htSvXE8hRDi4a0pU=",-459972462673990495,-6544829011846216175,-1063957178256608660,2881470096800358886>()) {
                                 case 491293628:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           this.bO = true;
                           this.gg = 0;
                           return true;
                        }

                        switch ((int)b.a<"s3h17cd1t7g4or","wOGYIaHoIkJSrRJ3e4jnn7KONoHwtKxSYuDgzOUIBYg=",-1446856815541809769,-8031908881925571438,-2523446706509999407,3345858430118445620>()) {
                           case -561594285:
                              break label79;
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

      boolean var1 = this.bO;
      this.bO = false;
      this.gg = 0;
      return var1;
   }

   public boolean bG() {
      return this.bO;
   }

   private boolean j(int var1) {
      if (this.D.level != null) {
         switch ((int)b.a<"s1lo34xsyudfsv","9fbAhUbawFYXmvosYZC3GEgLYSr21HyG6hJ4TqXkl5s=",-7123730048540276959,-8509505090818928570,-737184997187060560,-7590863976331593548>()) {
            case -571489860:
               if (this.D.player != null) {
                  BlockPos var2 = this.D.player.blockPosition();
                  int var3 = -var1;
                  switch ((int)b.a<"s37tnuneishjwb","5glS7znhG3rt4SU+zh9pZzC3xzqtbyAvLyYk5XJKD8I=",-449870263309235838,-5834447221039915401,-3114561199879513637,-7221681780588982627>()) {
                     case -885861611:
                        while (var3 <= var1) {
                           switch ((int)b.a<"s7n521qjt65tp","uXKbzjVwnA6ofZ8vH7Jw6SDF+wTUFF+RY3VdgAeFGgo=",7445631188462388772,2882663997854891892,6526012188845670996,-2031738859248069021>()) {
                              case 686918496:
                                 int var4 = -var1;
                                 switch ((int)b.a<"s3n3fj37riaxju","LnaTei3Fg1v9+zZ5y6bil3v8BjwjKP3n1BU63XpuPmw=",8294415901446151336,3591319784334003863,796632693588572675,4161764507124980170>()) {
                                    case 1246521500:
                                       while (var4 <= var1) {
                                          switch ((int)b.a<"su1ktlm35orgw","5+8E9IRR4CTj0tj6pCQtbiVatFPXHApGFmnSQ7PpG/g=",7929075818496612707,-3247533066500262184,-6430229731376773212,-1103184699289880611>()) {
                                             case 1335954409:
                                                int var5 = -var1;
                                                switch ((int)b.a<"s367y268o4r9to","laVflrI2/YWGEyxTFxfgvbVGHrmYNyxopRD/mT5x+lU=",9152932726585702095,-3158530682399707036,-7165760504881667404,-4028925496227112090>()) {
                                                   case -1851543222:
                                                      while (var5 <= var1) {
                                                         switch ((int)b.a<"sz4a0p7fw2sle","SGunruWrE0Tagkn3iXip0vr4pgvOT0qFwj2JmTmRsao=",-239986899351983665,1485533103288712317,-1661165008058832588,-7702839040490008366>()) {
                                                            case -1959778385:
                                                               if (this.D.level.getBlockState(var2.offset(var3, var4, var5)).getBlock() == Blocks.SPAWNER) {
                                                                  switch ((int)b.a<"s3mr4qi7chu8d8","uRE+RTfFr/NpEz7UmHAdqg7zpOeuEMrMiqekgz/E2D0=",8376703911066628167,7172239733126224999,311067227991793498,1044877237788733436>()) {
                                                                     case -1363873498:
                                                                        return true;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               var5++;
                                                               switch ((int)b.a<"s1871rafw7dsy","CSIrRuyc7ibkLfSEymEVCDdENaRcBrdPyY6EE4ixPtM=",-7086611930751069945,-5790413861339478858,7152032151544955762,2930773749179567058>()) {
                                                                  case 1923338194:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var4++;
                                                      switch ((int)b.a<"swdmvgw0g5pvz","DtNASEV6OMX5M0GhHr2ni6Z2OlqAalPUCW4te8vNgc0=",-6343871544005409640,7200696267725731042,7174298195904282301,-1977434148760936329>()) {
                                                         case -306211891:
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

                                       var3++;
                                       switch ((int)b.a<"s31qj4gwrkrbho","7vqY6xZo0mAUVAc9Hod4fV/oFhfmYllngrIqWuSe5io=",-6586434820175010405,-197934400101962662,-3600512420379276622,5183036298205252120>()) {
                                          case -476893426:
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

                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"sqhoj0cxdp7o7","fL02vdnHHzqbtW0E+IABjIfgS8ZbXtCdSdkxRuhOCCE=",-3555563915578791674,6452826847062500649,3183404203395175534,7081682569855997860>()) {
                     case 1482415865:
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

   private void ey() {
      this.bO = false;
      this.gg = 0;
      this.gh = 0;
      this.bP = false;
   }

   public void ez() {
      if (!this.bO) {
         switch ((int)b.a<"s1gryropn97t7e","fA2pLWuNCP09JPftYsQWzj8eGLGhCsr8sTU46/lkIdk=",-5542412384802446756,-615588572165776489,4597938228329546478,-7828791758666854569>()) {
            case 839664782:
               return;
            default:
               throw null;
         }
      } else {
         this.bO = false;
         this.gg = 0;
         if (this.D.player != null) {
            switch ((int)b.a<"s1zlq9c7lydho9","qbUn8kaMMTCXhkY6ktuLe7deOpEcO1/TbPOxuJ9YFEY=",-5839984858222575422,-5816413169895710553,5484448904963990874,5967188175017416681>()) {
               case -1204165442:
                  this.gh = this.D.player.tickCount + 2400;
                  switch ((int)b.a<"s1s82ckyw62qgn","jvnOuHrH0MxlsF7qFfoirCsSZHDO2fTitThgSuDHwEA=",7847456736349731034,-4581282986348660940,1201575514721277827,-19998618180542263>()) {
                     case -1631043106:
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

   private void k(boolean var1) {
      if (!var1) {
         switch ((int)b.a<"s1tjen6903sw9p","/zJLwOB833nobYn62/UEOx8r1DGvvyC4x9Y3UWB6KK8=",-1324895625275189945,-4789512353358282581,7386612411633168210,-8490746922084436380>()) {
            case -182418437:
               return;
            default:
               throw null;
         }
      } else {
         this.a.ag();
         this.a.a(this.ak(), false);
         if (this.bO) {
            switch ((int)b.a<"ssjgzqlw77bvp","BIwSRr6by5IwoZyKOjmmmpPjLABjbzcwlHTVwahibXY=",-995613046084472749,2662527508855354046,5382720927481093208,-4085091202273325164>()) {
               case 988533456:
                  this.K(
                     (String)b.a<"s3v1aeja1y0ge0","SbaeHdWzHP0I1LZAGPyfmGWoziqhbI0CEZD5TVhR5qD4Dm8CLGqbPR34TtYXNZ/pkvJhMraDc19WGW11JoiB1UDngUMFT+ssqGtpOK/zToEang==",-7438068952943772173,-8366120281464714006,1583363918311089245,-4208150070550417800>()
                  );
                  switch ((int)b.a<"s2mrnz1eywoayc","WIQyCjw5vuXmaumWEBcotwzZAxvEt4EAdP6Gh4ObcRs=",-7096705190977260839,-2371534383262003436,-6183046524047995484,-1424231068604782495>()) {
                     case 1503604602:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (this.bP) {
            switch ((int)b.a<"s1fd6t5g9lp079","Ksjfot/PuZ7GyQTjoLExshc4X1jYi77SYA6cEqxJ8M4=",5147438115993250381,3325405530002957161,-1390237792322385522,7749133900723471427>()) {
               case -1587142813:
                  this.ad(
                     (String)b.a<"s3gebb7qmi59p3","lT/SY+wpEvyzmoVg40IE1PMvMyXdLYhszkcgFeCrx2bpjxeLtmYSMGQTmV0RdbP4/zmnIUvDRMj4sO8twQi8jP/wsLdrLKZX/RXXmQjs",-1135058975117038161,-1890752519468743958,-6606846230090430673,8519910219017967389>()
                  );
                  switch ((int)b.a<"s2ahkhgxuswobq","uuVqKzSWf9iLSmgAIrTIRbCDq4+2Ab9G7GBIHaKk4tk=",-6439592572138559042,-8922994472479829359,7373224354266006304,8035772831721310331>()) {
                     case -2005761714:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.K(
               (String)b.a<"s2dlvlavqsurbz","Vohp18CETr2RDlX27avuBoLZNqOgMHi1nDOIbLQKXmbfUUpBsSf2DrVWILak49OZZn31t5wY07GyDG8MMUDaBkLFpTXEhdBLyG0=",5585258768064333436,3510429504916873157,4221049555942093617,-1802043518618149917>()
            );
            switch ((int)b.a<"s1mnsm9wq40wo","PO4wxC+QRI8Eu177D8wDm9b1tcpkToGbARPfCbkbxdM=",-7736549067987765407,8688384482639641942,-5679025779761111280,9017691084846172000>()) {
               case -488800413:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean D(String var1) {
      if (var1 != null) {
         switch ((int)b.a<"s10qd5y36kec9f","JxZOYR+HtTkRafGP617+IiANQLxMHGkvgPmn5AvXlwQ=",-7067662495958540889,-4534981518668167318,-2750095725139203972,3214947596350189247>()) {
            case 163705162:
               if (var1.equals(this.mD)) {
                  switch ((int)b.a<"s2hgy7h14mqj04","JgeWIrWXS3dRSnrAs39W9aLQkrI2gJu5U/ON+wVuzJI=",-4458312036659826830,-7556321721728883596,-1672293174599478367,-6373413889564482868>()) {
                     case 800614875:
                        if (System.currentTimeMillis() - this.y < 5000L) {
                           switch ((int)b.a<"s32ojsplswkxs8","FasYaXylP9X8OHU0fJXVQXCv2aTVZhRVLIA2c3B6eQc=",-4172252396120672448,6727290463707445911,-5824873518623024529,750319545961055476>()) {
                              case -277308749:
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

      this.mD = var1;
      this.y = System.currentTimeMillis();
      return false;
   }

   public void K(String var1) {
      if (this.a.ch) {
         switch ((int)b.a<"s3gfup9awaxotj","N2FVpaYuCZHWgBJhESu+XqZuXtQDwMW+wu7qspWcaEY=",3794933481834402229,-4067040675513765866,-3029306592817998024,2352583794638304990>()) {
            case -291377959:
               if (!this.D(var1)) {
                  com.yiyiaddon.d.c.a(
                     (String)b.a<"s2wiuwlx6a9ewk","oc7fcJ8VZD4vK7rGHXTQMcBHpHipoZig4cVNv8PtuUdP8L0q",-1934891433557063251,-1585985823197437748,-765522253584654704,7182927889503221946>(),
                     var1
                  );
                  return;
               } else {
                  switch ((int)b.a<"s28aaxbx1jkdnc","1b40p/1a8HYmnBcZQcpdXyult5WMR3p/lDAreYAJhx8=",-3148061277243879280,-7759220242384730892,7753678638116682183,-1768235438899694990>()) {
                     case 1717750895:
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

   public void ad(String var1) {
      if (this.a.ch) {
         switch ((int)b.a<"s11y2fkt197n16","Zw9Tpgt6TSMxryPJmZgMUpLAAE7eFKcjd8GjHXZx6nI=",-7287215195026674104,-1907431490088007666,-5272380413710565403,5357047973094124842>()) {
            case 331393236:
               if (!this.D(var1)) {
                  com.yiyiaddon.d.c.a(
                     (String)b.a<"s2wiuwlx6a9ewk","oc7fcJ8VZD4vK7rGHXTQMcBHpHipoZig4cVNv8PtuUdP8L0q",-1934891433557063251,-1585985823197437748,-765522253584654704,7182927889503221946>(),
                     var1 + ""
                  );
                  return;
               } else {
                  switch ((int)b.a<"s3j4y8acbpcp86","4G5P7YRczm5pZaQTUUq+zVeDBqicy0YI07tXtCqZyak=",225616171071979714,99128185873308011,4669654576590886384,8419201467252641580>()) {
                     case 549098921:
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

   public void b(String var1) {
      if (this.D(var1)) {
         switch ((int)b.a<"s36yupm94cadun","kuN8g+viyjzzGIK3FgVbr8jcA5E+074gS9A6qW7TvT8=",6313845240581051597,4139201163747077042,-8333560492220665959,-7513155289909763607>()) {
            case 1685440209:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.c.a(
            (String)b.a<"s2wiuwlx6a9ewk","oc7fcJ8VZD4vK7rGHXTQMcBHpHipoZig4cVNv8PtuUdP8L0q",-1934891433557063251,-1585985823197437748,-765522253584654704,7182927889503221946>(),
            var1 + ""
         );
      }
   }

   private void o(String var1) {
      this.b(var1);
   }

   private void ao() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
         (String)b.a<"s3i7f6hzuadnzl","MdyYA6CcrZwSWp/4ny0ZpwuVbsF5mLgwqacdSkMjlz2+TgzREhIwPIr1nRQ725FKQsqumgNp8FGCznrpi3M=",7934761108852471180,-3995832592165767062,8419880896379715395,6877740156305000902>()
      );
      var1.append(
            (String)b.a<"s3le6rx5cv40h4","3dmMU53duZVTpxXJkshkXsb8OPD0xGuzNhqPxPD7ZmgVVJgpyBjA0Ws8Xxcnos+1cx29MA==",-532529624677482335,-5514225694603883473,-4137185023654928407,-6707022510634354629>()
         )
         .append(i(bJ()))
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         );
      var1.append(
            (String)b.a<"s2p6y623boek4b","uA0A+8fwmSUGu6zWVI5ce/qW6PbvurL8l1DZAXB55tURxxmlLcxIgUoOH61DLaOrAryDYQ==",-9103438863024646273,-1874578293010833321,-2354820612860250669,5696509033688393620>()
         )
         .append(i(this.bI()))
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         );
      StringBuilder var10000 = var1.append(
         (String)b.a<"s9b595h2378h","q6KQCT80xNlGBjja/jxZxNge+Z4QqryVsUfjgYxSEc5WG3OwA0OWtNZ8+GZSax4sXr8zWQ==",2788208212546785583,-1321486285317072703,-1704358603306507745,-7352138567567092413>()
      );
      String var10001;
      if (this.bI()) {
         label25:
         switch ((int)b.a<"s27quh3k105fi6","py/68jSnrvd+y9OF9fx2SNjEeY6aIVDIkvREf3b5MFk=",-8012577645657635089,4726845904605846509,-5454253390311446351,-5045953477558096907>()) {
            case -703362092:
               var10001 = (String)b.a<"sghn4ct4vfnck","QAV2J77YB2qKntZs5vjX7CGTCVsdprRYmSzTgE2rVm0h55Zv",-7730928645127278063,524048811322059163,-3481771664728157309,4944776673084308413>();
               switch ((int)b.a<"s31y1rkuc4oxsi","W3Jh+mUEw/kB0LSbsZ2B/C3W792G58nPpZHLEpom8gk=",-3310474434062786193,-7798440814782881446,-6695873894682201425,2204756973858817882>()) {
                  case 1738496662:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)b.a<"s14t3avd6gcr1","Jgl0Ntig2yM2N7zYkLTwejgvb5dnbvtyPSgeZ8PflXo=",8661978503107733936,2888684795336419182,6656711921918369778,-2635934261337243427>();
         switch ((int)b.a<"sarnivh857a8f","lxeezM+XahAs7weduvuN0FISQzsAZPfZAPBGiB/Ywd8=",3483212550319724229,-1558411854149169027,-8343296975701575292,6534407532032972518>()) {
            case -1584423046:
               break;
            default:
               throw null;
         }
      }

      var10000.append(i(var10001))
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         );
      var1.append(
            (String)b.a<"s2anlxxz60gnks","c1WBy8lIjTWnULxZVxHQCBtHncVHF11nP+kX4EmyJ0E8Nexv4u/TZFxkvfz7UDJACRuyYQ==",-2752817170327225792,-1519793947068022342,1794853870760633448,-1044111088066141371>()
         )
         .append(
            i(
               (String)b.a<"s2ghig2yfmdud9","6m0i0veg9xizVByRbxxBlLd9TVMJK1kNYy5t4om58VjN5vrF",6729807373879270181,7410227312994033647,-2605975482324063250,-3961303122856811728>()
            )
         )
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         );
      var1.append(
         (String)b.a<"s3lvtkurym4kvx","fC9W50Nc4vaXgjOGxtwTsgl6ooyOU4Qr7hJrjWXFjVjdo2i8Pl6XlgEvIMepabDHNqr16fsXvjCow10+NQmO8J0pQyX7j+vj",-7138729195594729893,4586483775872376866,-482264746508832312,-3724233286359934329>()
      );
      var1.append(
            (String)b.a<"s13l81se2maqah","Eog2/eYcj6sW9gYFILAe0cUlqzj/nObmgDtRQPX6uqbisgQJ7ME0qFstJwaEQf080Ry2ohybywiYhjoLAOE=",1981939659443440060,6367551433318635891,-7404367458586614261,8660318040644574919>()
         )
         .append(i(this.a.gk + ""))
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         )
         .append(
            (String)b.a<"s1e4b9kkgei0mh","FG1GdrYdwKZ5yaW1PrqScV0rZo1X/cXqNbZWh2xFNXt3XnP0E5ZP8L+aC/c=",-6037509511206116415,-6070440538001664542,3982102421554641920,7257198365882180444>()
         )
         .append(i(String.valueOf(this.a.gl)))
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         )
         .append(
            (String)b.a<"s2phb56x0wqkps","x/0X2v0mwjeQObsRXvf2l3bKlcGfDU0HydW9UGu2/Th5k68bNSU9H1vN/Es=",2821579041351804850,-858842911568648250,-97776116139230649,5287055643345495425>()
         )
         .append(i(String.valueOf(this.a.gm)))
         .append(
            (String)b.a<"s1odvxnufhdgnx","h4wPkQRLoJbcIRQn/008eegjQ/J6K3050jaY3Ilkvis=",4210410644214040199,-652600277119234922,1046557082371408780,7706027661999920628>()
         );
      var1.append(
         (String)b.a<"s21pk85swb8a42","L4gsj7E6Cg92vhiYWnbrPN3nfAUFFLQZu5mxruRmX4YYQnZHkNFEZc+HAI0+IafwYcQBiDbFtVHeQzxHwYifZKurLakazx1zh3tEOVZGuel4NlmssoJFTe3690nf9cmAWnkbhamhP8uGOQ==",2157413013125154654,1554839379343901580,4000079705999667979,-8399564941149245162>()
      );
      if (!this.bH()) {
         label20:
         switch ((int)b.a<"s1jl25pgi3zuh4","H2VeiKwvojA2PxBoG2ubO8Na0iSm6iwbVFSlrd/0bfc=",-6815595769248548930,-6021903990746394221,6161256412257525353,-5623393322586570670>()) {
            case -1364047537:
               var1.append(
                  (String)b.a<"sxf17sfuxg0dq","37othT/Ld2NEqHIU/Sb3I31qUIE3bYrNb63zZJSQqniZNlSgBbD+/P9tv3ujnahR7gt9r/SbAX9duN90RXUmYVHZ8MdU1awSz63tBH6OaQCYLnHYPfGzw3dZRSK48stAhAhm5Zv79uo=",-4855120358713433220,8765741389160291338,6805606314890423943,-292231271377779017>()
               );
               switch ((int)b.a<"sh6bc7otx72xj","SaglIWb3z2Fk6CJtdv0kNFimvAd2U1HuXAumPjyjMTo=",5252371535349456205,-272415352209439546,-3268421937074499128,-4108515177477403862>()) {
                  case 1114023447:
                     break label20;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.K(var1.toString());
   }

   private static String i(String var0) {
      return var0 + "";
   }

   public String bI() {
      Item var1 = b(this.a.mF);
      if (var1 != null) {
         switch ((int)b.a<"sdzr066o29k2e","CKlfxdLRtrkz9tHwtZP/utQ7KjcMvHZOfvyo7Qxsg50=",4872098245379727680,-7940479473830200517,-9095353986910898743,-7556847565961467928>()) {
            case 223117977:
               return new ItemStack(var1).getHoverName().getString();
            default:
               throw null;
         }
      } else {
         Item var2 = b(this.a.mG);
         if (var2 != null) {
            switch ((int)b.a<"satnhvwnj8aiw","uWiRNdkaOJnGvO6+T9AgQr3HWFDNMU5nT2vcZ30kDC8=",8458527076728241536,-7691239634932125176,-2711342287879011209,-121380310195138472>()) {
               case -1771722592:
                  return new ItemStack(var2).getHoverName().getString();
               default:
                  throw null;
            }
         } else {
            Block var3 = a(this.a.mH);
            if (var3 != Blocks.AIR) {
               switch ((int)b.a<"sx1u7d703cjmh","VFhOR9lt7rFZILZwIMpCbyH+CUD/oIDmG2DpXTcgJao=",-5579480003833468711,-7959263279153786403,1020274223736719251,2615456393710739190>()) {
                  case -2122605534:
                     return com.yiyiaddon.f.a.b.bK(BuiltInRegistries.BLOCK.getKey(var3).toString());
                  default:
                     throw null;
               }
            } else {
               return (String)b.a<"s1hmdm8vjnhrr7","IYNWvmF4VHCkXT64Rxqv75+5vFUFvXwWh1w0O5RrJ8Ztzg==",6547859803034416494,-271948901487044931,-8223506086185965014,8429034062537515774>();
            }
         }
      }
   }

   private static String bJ() {
      String var0 = com.yiyiaddon.i.g.c.bU();
      if (var0.isEmpty()) {
         switch ((int)b.a<"s2ffebsnvyevul","VxoNfB5NmEavg2PGHF1p6vW9jrrdunS2Z3EVZBvlb/s=",2393454989780957975,6998809932819827309,5963822216897980414,5485370343527008157>()) {
            case 926846582:
               String var10000 = (String)b.a<"s267zjqa2jvg29","CesRetQEEJqsOBysbQElqx8pr4BLd2UV5LkkS+RFlQs=",-7543269982301294507,4234221713585803462,5677304915568741007,5447918936027698625>();
               switch ((int)b.a<"s2qvqqs9aswl0k","U4foeU+TQh/EtlI0Z4w8Kj9EvtFUmsWL+0McnI1wsVg=",8954157833815695613,4273413408923385194,682093531766878420,7458407959651507993>()) {
                  case 853241124:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = com.yiyiaddon.i.g.b.bU(var0);
         switch ((int)b.a<"sbedl3ggxa38l","PnkSvlUoz/HouhI71zRM7DKX5hSkog0KGm+UiEAp+Mg=",-7553340076517698091,286985665940916726,-7243116123091912145,-6236175499509288814>()) {
            case 753534009:
               return var1;
            default:
               throw null;
         }
      }
   }

   private boolean bH() {
      if (this.D.player == null) {
         switch ((int)b.a<"s2ayfnh2a4ozyd","MkE+E2v7SUhaGpCiPXIv6l/N/7Lmh/+3TMseyS3+okI=",3675080207935348450,-5459767711678913912,8860590033837402088,-2214206432736353958>()) {
            case -508783058:
               return false;
            default:
               throw null;
         }
      } else {
         int var1 = 0;
         switch ((int)b.a<"s1vah6zzzsg2ep","Cv/cNgqSXPjtXU6xJjIpOtWL+E5VKBSG2tS6Yw1+CF8=",2044067356347012306,-7058824131319371983,-115125287976923964,3311877818038085979>()) {
            case -421210012:
               while (var1 < 36) {
                  switch ((int)b.a<"sndsrntr3sup5","/OR9cbxQEJIoHVefLST3M2aM9cv7pBLO88L2beeT6ZE=",4167577968700368706,-1603839638556208698,-2235564649971491125,-2669665011726990734>()) {
                     case -1315771930:
                        ItemStack var2 = this.D.player.getInventory().getItem(var1);
                        if (p(var2)) {
                           switch ((int)b.a<"s3kn79s2dkujrv","mo88pKCTDpx7hpy6HJN8mhf/0/if7FYMYVpDXUi6aCg=",866858468708379691,7758819007036304233,4518554694410879623,788284736075698187>()) {
                              case -1408162721:
                                 if (this.a(var2, Enchantments.MENDING)) {
                                    switch ((int)b.a<"s3i1jw64nqj4t3","GB8OYh7sgbFXMq2qXQRZufOAUdHIILAoD257t3XLSww=",6400997502189849548,8780118559617399418,-9222286136011079891,-328328957201838262>()) {
                                       case -732552552:
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

                        var1++;
                        switch ((int)b.a<"s2zgy9urutt928","E+iGJU7ybb2SlPvxEPxC1uJUDNoZi+8hGQT7WWgANAQ=",-1026179794869182926,6461489245767710119,-6496825998213344102,-449629816426372461>()) {
                           case 575057439:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               ItemStack var3 = this.D.player.getOffhandItem();
               if (p(var3)) {
                  switch ((int)b.a<"s2iqh4f0nkx9yy","YdDVTxTXui/iMBTMTQM2DUeoAkDLgfet5Xx073kU2nU=",-2171042882289338432,3964713654623454534,824240477521205760,-294320473504651681>()) {
                     case -1937985062:
                        if (this.a(var3, Enchantments.MENDING)) {
                           switch ((int)b.a<"s3h1rsc9614s81","dkLFfRV0AmsD4MWPOQOq58AA/tjHkauZOiqhlNR7kyM=",7491164675489562918,-6389654288695852764,5998118430858407533,-8602421576490957899>()) {
                              case 1280669275:
                                 switch ((int)b.a<"sbzhfxh5j8n49","BvMtJJEZm8USGfm68NunzV9D9gCS9GcbPIhRLMdXWXk=",561711616907182682,-2993855704771042640,-3866992997602236321,-6173397829265475265>()) {
                                    case -419802836:
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

               switch ((int)b.a<"s1f3bd3cbu2rnu","vOntWcK1jFb3ya/KZYg5RNza8hCJx9truMDIhX9LP8c=",-4009539046134796194,2855895530402338185,7198946029868085118,4102634286320636172>()) {
                  case 1317186529:
                     return false;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static boolean p(ItemStack var0) {
      if (!var0.isEmpty()) {
         switch ((int)b.a<"s9w6oj16qy8fm","wyl6WEbIGZYCO591l+uLMgOxBmDljpxmpHFUQEMy8l0=",2834814721945974965,-5755834631380931550,-6104096673855782672,1727164655803683124>()) {
            case -964159038:
               if (BuiltInRegistries.ITEM
                  .getKey(var0.getItem())
                  .getPath()
                  .endsWith(
                     (String)b.a<"s2l317euc72gq8","KlXUHn1SNubGKXwT2rZTQsMRScbbAm4HdmOi0EiTvl853Eqg3PNYVwQP2fY=",1796462580151055171,4922256409267875064,-5183589295448413646,-7341772316984718577>()
                  )) {
                  switch ((int)b.a<"s37vtawt56v101","jGpGTMPxmUgdGh3E+6XonAM6kH0FwL0+qDuQqePGWHs=",-6656034643763630814,-999583448466431668,3714130803172916692,1997189114923330294>()) {
                     case -1932742615:
                        switch ((int)b.a<"sn868lx1wmuq1","4blsmD3nymsOmUHai6qxn7iABF6rNoZUwNkbC6hQ+NM=",816987221678447563,-1928250164824520081,6012056655318771482,-8739565791213685456>()) {
                           case 1368340319:
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

      switch ((int)b.a<"s1amesyk724j04","DgI0QrpCyxga6YN5Ez8VGttB3UYM/ExEQcwgBHQ+qCw=",-4830318639442835628,8981945921710707299,-7271825220535524520,8034964664351511567>()) {
         case 1363089314:
            return false;
         default:
            throw null;
      }
   }

   public com.yiyiaddon.e.j.b.a a() {
      return this.a;
   }

   public com.yiyiaddon.e.j.i.b a() {
      return this.a;
   }

   public d f() {
      return this.o;
   }

   public d g() {
      return this.p;
   }

   public d h() {
      return this.q;
   }

   public Block c() {
      if (!this.a.mF.isBlank()) {
         switch ((int)b.a<"sqgwxu7uuav8i","3FPCEkjZMUnqRrdrCiIkuUlXURdQIdLx+ttzqWV0YkQ=",-1807503191539258430,-5359114059863093837,-1536449052353108851,3490086576525576090>()) {
            case 53835993:
               return this.a(this.a.mF, false);
            default:
               throw null;
         }
      } else if (!this.a.mG.isBlank()) {
         switch ((int)b.a<"s2x0xcdutu5zcj","5y3GPn2S4vCD3lulO6NOwmV4643RS8w/RjYsOHZTeo0=",-1866890198240402193,-1397397421237433752,-2571236379622090851,3505704677838941363>()) {
            case 1498361021:
               return this.a(this.a.mG, true);
            default:
               throw null;
         }
      } else {
         if (!this.a.mH.isBlank()) {
            switch ((int)b.a<"s7kqbi30xwvmu","O7k5KcWVwXe7J61WaDJLl1hF2nXGc8NDxiNVSGGGjgk=",-1081408600737558099,-161070994802709864,-2748530930673593742,-4416440332988773177>()) {
               case 440119514:
                  Block var1 = a(this.a.mH);
                  if (var1 != Blocks.AIR) {
                     switch ((int)b.a<"sv9mguu8893s2","RTpuOhgEsiZTrY3dv2ZLbwK/pY4ZtFJctSX2vtQxUTU=",2128323180607387990,8622296816774166741,3371027171764426749,7599024770069560964>()) {
                        case -508760234:
                           return var1;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         return Blocks.AIR;
      }
   }

   public boolean bI() {
      if (this.a.a == com.yiyiaddon.e.j.e.b.SILK_TOUCH) {
         switch ((int)b.a<"s1m7j28ivzk65","/9+7FKhgOC3KaHI926IHZ2Xrq1lE3zT8lyJgMSUAyAA=",-4106121965893988773,8573610047339814598,7746374138335878814,-2679520953324553642>()) {
            case 435815180:
               switch ((int)b.a<"s1gd1tkw0dgwdz","ssCNFTY7+mkLzbFVEw9SgKRMSu0OK623/SYXWuE2F8s=",1273795174579885409,-2433422507613029806,-5693585470622587914,6171156489937948844>()) {
                  case 1073982055:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"spprvyjfdlaha","nup6VTpjHjUHwIP87LKNiYFZpCSwQIa2PdraLXJ6vPo=",-6457525694218295124,3320134099460624162,4874157936196727306,3019088362340094588>()) {
            case -1934197691:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean bJ() {
      return this.a.bS;
   }

   public boolean bK() {
      return (String)b.a<"s8bbpozjf992z","RckJAbkxrgi3haCHKDPwObr/ecz1HFy7J+DkogBtY4DIGOvm96G5wNw4OGGYnbbZfxq5XaiVz5T9Dq7W/XRS7JDECn8=",-5911468708993884306,-99587488149190234,5481848503633834602,2678043756243459985>()
         .equals(com.yiyiaddon.i.g.c.bU());
   }

   public boolean bL() {
      return this.a.cc;
   }

   public void eA() {
      if (this.a.cc) {
         switch ((int)b.a<"s2d4iuvjxwzd4q","9cC5j5lZuh4mXMSCRYdsNveyO+Clk2qbPuKdpFDX+5Y=",-6764182958593051014,3437537628687650451,-7681871603370583291,-4670448259609409305>()) {
            case 613560115:
               return;
            default:
               throw null;
         }
      } else {
         this.a.cc = true;
         this.L();
      }
   }

   public boolean e(Block var1) {
      Block var2 = this.c();
      if (var2 != null) {
         switch ((int)b.a<"s32i3pou5s1e91","R3Lae70ud/3gOAbN8MujGm4gtxV08YlJpA1YhFnDVgk=",7133529167280103628,-5640925581489662295,-1906499344011919222,-1573124093046322342>()) {
            case 1007776817:
               if (var2 != Blocks.AIR) {
                  switch ((int)b.a<"s3goitcu1dvt2a","+EikTY/KLvt2XuYbKG4xbvQPKXaFfY1f+llhvl4CqKk=",7494152760799018999,1887190979944952618,-5777702904210819130,9136438646628580295>()) {
                     case 1857893741:
                        if (var1 != null) {
                           if (var1 == var2) {
                              switch ((int)b.a<"s3t05er86agiub","7naa0AzCNh3HCEVBjl2oFD4M+vFykz78Z2J2hsS5Qpo=",5425004836500551522,5743897666946941483,-1560197830154338142,-2511693743448635781>()) {
                                 case -943654633:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           String var3 = BuiltInRegistries.BLOCK.getKey(var2).getPath();
                           String var4 = BuiltInRegistries.BLOCK.getKey(var1).getPath();
                           return var3.replace(
                                 (String)b.a<"s1riicp2vhxq80","57g9vMjP3o9chJwX6nh/IOTqeuT345/Z/aqyCormkUCgOrtgpCGATazejg7mTbco",-875632151515309580,7682729905045325531,262274639026092308,5373158861354622351>(),
                                 (String)b.a<"sfornsv11l1rp","jemxH0jMSF7znVG8q9+qQ8JJq0TB/Pbn4+r2SA==",396220849542488325,-1298840193335610311,-6643955711067199901,5703609369823525931>()
                              )
                              .equals(
                                 var4.replace(
                                    (String)b.a<"s1riicp2vhxq80","57g9vMjP3o9chJwX6nh/IOTqeuT345/Z/aqyCormkUCgOrtgpCGATazejg7mTbco",-875632151515309580,7682729905045325531,262274639026092308,5373158861354622351>(),
                                    (String)b.a<"sfornsv11l1rp","jemxH0jMSF7znVG8q9+qQ8JJq0TB/Pbn4+r2SA==",396220849542488325,-1298840193335610311,-6643955711067199901,5703609369823525931>()
                                 )
                              );
                        }

                        switch ((int)b.a<"s2q4mipmqanvix","Nm5iHxeSra1Fvl7TdxVnQn5bJZXi2fENaGeFN6Sfpp4=",7674138735421529889,217099402004648699,-6867998116156560246,-7989724787422880535>()) {
                           case 2006088408:
                              return false;
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

      return false;
   }

   public List<Block> al() {
      Block var1 = this.c();
      ArrayList var2 = new ArrayList();
      if (var1 != null) {
         switch ((int)b.a<"s25xvi0qtoibil","ZFeZvA8YX+k9MNi1607dc0PX1x8BNKsb1eN6DBDuHrI=",-7851305193904065555,8271441562217737743,5101339851566856308,-3810218743841646066>()) {
            case -2092294993:
               if (var1 != Blocks.AIR) {
                  var2.add(var1);
                  String var3 = BuiltInRegistries.BLOCK.getKey(var1).getPath();
                  if (!var3.contains(
                     (String)b.a<"s2qceeg9xjihso","d+URXLkRIRETMzwqOGrA2/x4PrSoUAMUIW2ush04uoJb3kVl",-3529376989139755155,4829571212643619987,-5940073486101278557,8794515326123230234>()
                  )) {
                     switch ((int)b.a<"s2pcko6s0tzvtt","NGm/u9lce2lJ3JeP3+WMnjRkzc6Xlpj9hnz7C9EXRDo=",-2562839323103997562,-652389504752296537,6379229559070830538,-590453011531252658>()) {
                        case -851948727:
                           return var2;
                        default:
                           throw null;
                     }
                  } else {
                     String var4 = var3.replace(
                        (String)b.a<"s1riicp2vhxq80","57g9vMjP3o9chJwX6nh/IOTqeuT345/Z/aqyCormkUCgOrtgpCGATazejg7mTbco",-875632151515309580,7682729905045325531,262274639026092308,5373158861354622351>(),
                        (String)b.a<"sfornsv11l1rp","jemxH0jMSF7znVG8q9+qQ8JJq0TB/Pbn4+r2SA==",396220849542488325,-1298840193335610311,-6643955711067199901,5703609369823525931>()
                     );
                     if (var3.startsWith(
                        (String)b.a<"s1riicp2vhxq80","57g9vMjP3o9chJwX6nh/IOTqeuT345/Z/aqyCormkUCgOrtgpCGATazejg7mTbco",-875632151515309580,7682729905045325531,262274639026092308,5373158861354622351>()
                     )) {
                        switch ((int)b.a<"s3ekqnx2hiq6d5","qtfyfgeyon42NwmziVE6yYbHIHtI0pOA6PC06EX/Vjw=",4172887179066817288,-4349723455314223236,-9076011983488500610,4364157997753325221>()) {
                           case 1481566742:
                              BuiltInRegistries.BLOCK
                                 .getOptional(
                                    Identifier.fromNamespaceAndPath(
                                       (String)b.a<"soy7p0ubzl0yx","dnFNoVgBNc9J0LR2e3WMiNmjqJDUk4EVFMMixvrzfN91GrxDKpLsI/WyawqZrg==",-5551984230483525527,-258204810820669767,449125222088229435,-2753648807849723956>(),
                                       var4
                                    )
                                 )
                                 .ifPresent(
                                    var1x -> {
                                       if (!var2.contains(var1x)) {
                                          switch ((int)b.a<"s28b4wjcuycx8z","xUVZULeh3JV+8tBXI/8DkhG6tJDd4D0mhE1TzDdxIj0=",7086916818947910612,-1887809508403356882,-8160405684580805896,8485887495559631839>()) {
                                             case 1897084281:
                                                var2.add(var1x);
                                                switch ((int)b.a<"s3hmv21pfcrqna","s/4L9hoxENmoGVZ8/NJKTwWgDf4nIROl459whuCJ4rw=",-490446228157897520,-7812073551981458569,-2330977809766118533,7284012683954064041>()) {
                                                   case -233849874:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                    }
                                 );
                              switch ((int)b.a<"s2oujzce3e5zt","8w57azwUxtkwMLua4eESO7HJ1UQpjuXUtFFzRN4zelY=",-8084899356926814537,-3148273335638548375,-2556183314683634983,7776702962286038371>()) {
                                 case -927562786:
                                    return var2;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        BuiltInRegistries.BLOCK
                           .getOptional(
                              Identifier.fromNamespaceAndPath(
                                 (String)b.a<"soy7p0ubzl0yx","dnFNoVgBNc9J0LR2e3WMiNmjqJDUk4EVFMMixvrzfN91GrxDKpLsI/WyawqZrg==",-5551984230483525527,-258204810820669767,449125222088229435,-2753648807849723956>(),
                                 var4 + ""
                              )
                           )
                           .ifPresent(
                              var1x -> {
                                 if (!var2.contains(var1x)) {
                                    switch ((int)b.a<"s2zlwspljeoyro","ojCUWJE3F1XMEcocs4RtahSG53J4I5Ci25k0fCCEafA=",-1064435085641605650,-3639149065851181551,-2740722904322598055,-1995255922107903619>()) {
                                       case 216270492:
                                          var2.add(var1x);
                                          switch ((int)b.a<"sq0hq4gv36x7e","g0uWl9b8JePKPjlpcXcJDwE0x7aYMbiNyMqrPfU5bHs=",-7006856650898141831,5621794976541417484,6752180828283713797,-3008952253723133008>()) {
                                             case -1031018630:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                              }
                           );
                        switch ((int)b.a<"s42kamrlouo4f","flMQPuJYh7X237vTjYYwgWlv2s0HrKBL269RpaibiBY=",-1409661964008992977,-1101966843654233157,6027934904218490653,5069015758328347033>()) {
                           case -308709556:
                              return var2;
                           default:
                              throw null;
                        }
                     }
                  }
               } else {
                  switch ((int)b.a<"s2eexj6k11g3y1","pa4R3wOlk33IAcNGAZc0aHjdkdTzCedK8k474/uvH04=",8413697910295179037,-3188158477338350717,-1334201088733879893,-4298579517042379703>()) {
                     case 2076960696:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public Set<String> r() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.al().iterator();
      switch ((int)b.a<"s1v6tb8ei2fi28","StruTIgQq8piw33kCAnDWAUOlmiaW9Q31MKFf3nR9X8=",-5178466140548036181,5209216192269621566,1705063238465697069,-331293982777373502>()) {
         case -337432407:
            while (var2.hasNext()) {
               switch ((int)b.a<"s36d6b55ohqozp","M8XxN+GOusbAXrV9lNWX3UwF3Hg/F82OZD7xUtF0s68=",-6203788470755257198,-5186587359561145661,5551488993244850304,5462814523894030344>()) {
                  case -1188566590:
                     Block var3 = (Block)var2.next();
                     var1.add(BuiltInRegistries.BLOCK.getKey(var3).toString());
                     switch ((int)b.a<"s1agnjwdnspcd6","cs014PVToW899b52JJjh+hBxZIPdY+IrPWxUAjhqeBM=",-234058040118256477,-2738260328503042042,-104511293572451392,3211819867794076458>()) {
                        case -1109303084:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   public String bK() {
      Block var1 = this.c();
      String var2 = BuiltInRegistries.BLOCK.getKey(var1).getPath();
      String var3 = var2;
      byte var4 = -1;
      switch (var3.hashCode()) {
         case -1873763316:
            if (var3.equals(
               (String)b.a<"s1pos8g4nxmf2z","cRBfA68SbsY4B71B0Jq6iSjORZtzg3RmS8BZ+iC/afmctZEV2+48hY0ycrdFSCCyVbXoJHWwg11gCHPKxFiXhA==",-8785482968685377940,-6499108717342750222,5318417247982715017,-7458125883249193075>()
            )) {
               label213:
               switch ((int)b.a<"s3epnc52gf93a1","RiIvOnPmHeNk5miGqPz4AP/Sph3Xx6Hfyywm+Hx6dCM=",-1880752798830869839,2365830801308043974,5508802433755558651,1050033079573285546>()) {
                  case -1282110659:
                     var4 = 5;
                     switch ((int)b.a<"s3oagbg80ah73m","DkWdMLul9QdSha+eeUZroUv1K2QPBZpuE4N6BDzcujw=",-6703796794027292144,-195590537594280064,2896327766916102702,-4391462035309881883>()) {
                        case -937036258:
                           break label213;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -1760410398:
            if (var3.equals(
               (String)b.a<"s37domp2f6cwja","89oaNL3GWCijD+3Ji5bI/GOjFhg2ZmoICzKo7FfarKO3gxOPx3nCxnQaWR21mVHLRCdJDQzb1D0=",4348980585291423954,-1926425857158760079,-5150405137501749416,-5236496387423987198>()
            )) {
               label186:
               switch ((int)b.a<"s1dc4xnxl9lauy","EepZHmb3GjQi22lTIXeYknOpy2xt7rgCdIZq1FYlqo8=",-976822277684407820,8910938441672152673,-1220945973121689385,-8527492594317118618>()) {
                  case -2080728588:
                     var4 = 18;
                     switch ((int)b.a<"s2umo5pkjs2j2x","CK9OUx7+uuD7hbmCXlGa3l2mcOUuJMEunYzCECMeHE8=",-6678924767627611398,-4160707953170714527,6154002643755905007,-7348203987292755968>()) {
                        case 1698484792:
                           break label186;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -1489498011:
            if (var3.equals(
               (String)b.a<"s1fivhzkexyp1l","rSHICd0eHHD6lfoODXpjyBfc9fc/FPJFUb2FurTQZW5S6ppWbUT1fiY06aEqVPTX7HqgMookrgHuWWg/FqxtbK/rJxOiew==",-1785251607359688776,-721325275903715643,-8666144769693493829,8129513125773385470>()
            )) {
               label192:
               switch ((int)b.a<"s2yl33ybdh2lv3","dqdyob5OALMeA82Ynien1WGC+2ICGtzM2s3VUJdYA0M=",-7886106435837548936,8739020270831968805,-6675566455612758592,2955181530791743778>()) {
                  case -1127819089:
                     var4 = 7;
                     switch ((int)b.a<"s2m6kqeq5h1cqd","mh2mOb4Db3K3xXkk6yxVqfI3SWfJ2xnk5JtzQuUU+jE=",9075854195844660906,1174222580715065591,5441878039196917523,-3315437761148558427>()) {
                        case 1319382418:
                           break label192;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -1293858290:
            if (var3.equals(
               (String)b.a<"s3am1yoa6f31xk","ZLqTdXE0273kFX3E488DRRM38lFhUQf4idCQ/fODj3BNnkgtLN5lvxbq6RKaCxppVLVRf5OCOX7EMin8YWnk3ObucdQ=",-3355748795970462219,4664318435851710608,-4577183547350002656,7509692217545707370>()
            )) {
               label158:
               switch ((int)b.a<"s18uhrsjzoivzx","dMbyfRo1qwAA8ZRVZpm0JNUhhoyEeVZ55MO6VeJ3tOw=",-6529663307832071483,-1984232548790407800,-224285647477189459,-5945892604934300446>()) {
                  case -222144655:
                     var4 = 16;
                     switch ((int)b.a<"s129uhpqq6txd0","PXeSF3GkwnuL+mTizIuPXZ9vPK2+LtPfPlwaAQhpDeM=",-1277518179915101077,5731868881376844179,-2473933235353692415,-3968544048284727351>()) {
                        case 1105711577:
                           break label158;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -1236636995:
            if (var3.equals(
               (String)b.a<"s7wft2zou37sn","mhtgBYjzgXBQRaF38lwovf/RofcwWmn+77EC5qzXI4ltPb0Ao7QTCHee0cESglLmE/Pnb2r66nVbf+x7HWu44A==",4333129342243679414,-2807256628959801671,-4269106737256584850,2569874135096071485>()
            )) {
               label221:
               switch ((int)b.a<"s3iyvcx220r5y0","UF0v/RBK4JHZmBEl8s4vHLODOl951omnhdKERUjq5iU=",-1499274838995262531,-7087518681410092855,-8768234621624040182,5299507458480814943>()) {
                  case -1134591286:
                     var4 = 14;
                     switch ((int)b.a<"sbzzgivmy8d89","moDgd/CVqYoksgBnhP3OHynunKIX7htoGxKjx2EEHmY=",-4788834019488728333,5303644308734234957,6030814093067407375,2319619611508321948>()) {
                        case 595807491:
                           break label221;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -947066822:
            if (var3.equals(
               (String)b.a<"s3drnvlgwxcqna","OTzgg3nwPixFbtAJdwuw7RIp8GXw/EoXHBaIvyoeE4hcVOFBHP8oBeZXb1E=",-8146794752811865378,-7717225618266449476,761503898777717377,-5258660260051895834>()
            )) {
               label178:
               switch ((int)b.a<"sluqmm74gcpyu","m+suYMG/Z+LNoRK6h1NmqUjR2VCcYtlOn5iVGvyA8fo=",3622160874465237551,4740157811394639045,-4496641474925351974,-4365336811188473834>()) {
                  case 376724942:
                     var4 = 4;
                     switch ((int)b.a<"s14lpoqpibv25z","y1xs98WV+B0figCm+nliGnkZR3NjMNl23P3fqPjmnyY=",-1270926220157142056,2383759269193220680,603942408651916811,-6842675048650033325>()) {
                        case 955241873:
                           break label178;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -946272344:
            if (var3.equals(
               (String)b.a<"sxg3dlkw60in5","ZgWOdgnElLv81pqalZQmCjQsE+vm/xhotiJdN5roOV4ZR66jiHo/RBVB9bZpbw==",7183998549274765093,-1822505171981463848,-8421404996323241462,4773822525455464933>()
            )) {
               label155:
               switch ((int)b.a<"sl7it7qu8c9z7","eQHPmO5WaFhNlZssTclZFnXJREGlc+Uy6NAldrye2s0=",-8464314380978271493,-312257138925247118,4055430346383770144,-5191894456722483901>()) {
                  case -427485483:
                     var4 = 0;
                     switch ((int)b.a<"s32y269fsb8ykp","p7qK8Eh13YG9tFt+Ts4d2QV1u1QrnjT0HV4Q41jrPlA=",8822068361907285206,3764222151681776877,2692804047790841547,5353506180307550450>()) {
                        case 199809715:
                           break label155;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -309940501:
            if (var3.equals(
               (String)b.a<"s3iatwtr925jxt","VmACQMoxYM3Pw/bGuihfjL/Nf77ynOkR3nuif+XRqJv24DIpqWH2+IU1vck=",-4341158696541541195,-973845502834957664,4022200801292747346,1506456304688238833>()
            )) {
               label171:
               switch ((int)b.a<"smn0plrxxbx53","goziS76lhUONpx5kM6S9gZlI3fHFE+WnJxaw4cvayTU=",7167444128735959635,4356663206754556507,-3999704408735023650,1475784265920748860>()) {
                  case 2109057146:
                     var4 = 13;
                     switch ((int)b.a<"s1ipwt642sargc","bQt4hsEbFYpaAv5OnPBWy1M+8V7hnJzunGqLmAEzbUc=",6255535788358637675,-7087495731125383349,-4332722918267710306,8399779248565228928>()) {
                        case -370338981:
                           break label171;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -234055030:
            if (var3.equals(
               (String)b.a<"sx7hogckayu5","Lzn28N9No5xbSi8j2pp7jpifi13X5N8nqarLXYkJaWZ6YsPeLAZyaiH9nCcmqKstrYyXde4Ei0qiDw==",561713611368027752,863066080967829993,-4994927872871173169,4339094153580913582>()
            )) {
               label182:
               switch ((int)b.a<"s3cy6ubhx9x56i","f1TYWvzq9lOCQpvpnMZHlF0rKQwnXSK2D/ElqSX3MIc=",3909810768217167640,8998766729495535529,-2591895229386870970,2453644027960662859>()) {
                  case 1422260862:
                     var4 = 12;
                     switch ((int)b.a<"s2q5wfbp84i1xi","FcGpf3OyKBaFfD1G7lMzp3eA4KsVe9qcDnAybThQ0RE=",-9120552731045096125,8791124775430790461,-4433529150985279167,-3105527588251645127>()) {
                        case 542343027:
                           break label182;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -166627689:
            if (var3.equals(
               (String)b.a<"sobt02uqoarr1","3o1OnqI/7etwAqQ7W64pTbFn6aOOlE3SeOUgM6//2LZGD6GbH0SsiAErNKinAKnlNMINxg==",3951729223210026443,-1025194941665428896,7730630162800990676,-1488756099076043633>()
            )) {
               label164:
               switch ((int)b.a<"s26xh5ymthfu5u","x/jUkWTxQFv3byjPkTzsEGfOFLWC+8E2F8HsErnROfg=",-6161849894652196759,1270540734306450649,-6893992603932768190,-1971396710337447572>()) {
                  case 1661213119:
                     var4 = 2;
                     switch ((int)b.a<"s3jwsq9nag7m0y","B/WhhIVhICusocWRR10sg65ooqgFsye4+Q0R9mXkGfA=",-6112018883693218156,8483421290121803493,-2320283479574377201,-4496872512160559241>()) {
                        case -364425649:
                           break label164;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -66127511:
            if (var3.equals(
               (String)b.a<"s9t900h3s7phm","HvvuIOg7t+VrspbSvtvFmQnEoROVQX3Gpo2E9VaAIPzvfZisdvb8GFDrQS+pCF2eQyqK6hW8e7eQg+9bbJAuXUA2ieGrnPiJ",-1403627604115627011,-5652897513280715186,5071365371984608548,5296629557427304141>()
            )) {
               label205:
               switch ((int)b.a<"s2rhal5lu569pb","XQJyBYL5NrhhNUnMCLjFUV5qmpPtT1GC6wKC5swMu14=",1863517059810196002,6647810383838644050,2513254935259308198,4867815654196465156>()) {
                  case 2060321448:
                     var4 = 3;
                     switch ((int)b.a<"s2atfhpixkpvjp","xwwkQflrNn2arnXVRZhss9iNK4G/fDCTpRmxszRli8g=",-4349633822286533542,2007038940337610072,8085058006162813756,-3722937077016908812>()) {
                        case 399295144:
                           break label205;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 203242172:
            if (var3.equals(
               (String)b.a<"s1tv9e30yv0mp0","SqjZJe3I0nI0xNM30xhNf3HJHwq7lkV/zpn3nquBT7e2zpvluVPN23iuTkTCDbuM",5070255884840680137,-8474804909417329363,6452191633475925824,1227865033033490285>()
            )) {
               label196:
               switch ((int)b.a<"s3rxdob61b9nzr","hVJBTW62WodoxlU6fBHUqs2U5q4KgUwzhqI2B6UZzuc=",-1225716788700701660,-7556846795653994306,692302712921960321,-6827669952213309144>()) {
                  case 1452419230:
                     var4 = 15;
                     switch ((int)b.a<"s35nzzee3m6qiy","2XGfXHYdlFbvQnlIamVyNWmJ2f41uLWoRBDoZYPWKKY=",1194923558102194271,-1909699643182775268,-6581284772223646130,3780687096257981809>()) {
                        case -745599935:
                           break label196;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 390907414:
            if (var3.equals(
               (String)b.a<"s5oxeiymff6y0","89sB3pXJjq4X1jCtSLdDgUTntJmVgTvbmGPuBVaUE1tAqm9IznejhUAbi1v+B6prwrwvES6uDsHhxUNt5e5c2p9M",1372338586959376228,-1727260571482092888,7127338322568593085,-4514663384669796049>()
            )) {
               label209:
               switch ((int)b.a<"soyj9n5acgje1","dUwTeVaEvzahxLjbZj2ckGvjvY31qpNLMLrcR6dE+Sk=",9194879627502936363,22246202213693133,5394499222432466167,-3793267432721756104>()) {
                  case 27660991:
                     var4 = 1;
                     switch ((int)b.a<"sr5394eiyhl60","L7L0/x4yj6cEfwcNe1lYtuNbE8Z9q/dgZ+ZDArme6sw=",-3882723047549559774,5836684073027072159,-2439935789035948305,-2361420093527431631>()) {
                        case 1645442075:
                           break label209;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 941346181:
            if (var3.equals(
               (String)b.a<"s14x11pboojt7r","NKI3mbKFgsJ7hMqdArEodVEg4ihxap0Plh+lXWPRKDlm74TDUY0sl72IhQYX5Fnm3ZcHuxnTBce1DHeE0ZA=",-734356086223094104,-6930082671398801694,1948844886785576156,-9166948138436264990>()
            )) {
               label168:
               switch ((int)b.a<"s2a598668omcq7","CG8NMPyDe1dVkYNsKc+gnPtJ7gGncmZ/T0tskKw1SWk=",-3740095157010851062,-132765191796518302,-7468162268970156115,-8298397516750565298>()) {
                  case 181156976:
                     var4 = 17;
                     switch ((int)b.a<"s1wi0xn3fr9w0b","4xkjjLPtem9oOFrtWLneM9SJYWCyl4Ja8QXtehBbsiI=",8092182310733085028,2256008407666905973,2721705381092388895,3459659658435711580>()) {
                        case -575562948:
                           break label168;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1110043221:
            if (var3.equals(
               (String)b.a<"s3g9dapn7scidv","CHAxoIR2PfbxG4TUe+cBkIhjSRCEHxWbOfsZLrKovZUo5ggck0sCXIxQ0aEAe0s7r7Dasc0eQL8P/K5EEfQing==",9089186824384961052,6693685840574423891,8190657552782450369,-146708957577095381>()
            )) {
               label217:
               switch ((int)b.a<"s2x6kzciar4jlz","/Hca0sk3DulFc30LaI4ZEXFADj/iZCPN5b219u1Reto=",9049464856879587579,7487899501033558136,-7869076308607361311,-986070471376312948>()) {
                  case 1090577077:
                     var4 = 11;
                     switch ((int)b.a<"s15avsyyqxjlf5","LhfzyCfqzqw1odlA6OuGGo442lOngeN24ddWauJUz3k=",5920580210191676971,5019276920193552530,-8539542340314067881,8279679341117307493>()) {
                        case 1161830272:
                           break label217;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1274763847:
            if (var3.equals(
               (String)b.a<"s1c99mplnxgrz","HSdeqoAkgdATe8fHl3sLaxsFHp4Q7KKl2gLLpfvcZchZ3Oamqx++a4S/zujlF+IPo0o=",-1253833988274491872,2937615030405866163,-442748365275461069,-2314936904067389775>()
            )) {
               label225:
               switch ((int)b.a<"s2ph6r9etaswpq","W590axuwdzX6zZAtRukw8/pm1uNtLH2IyKa9HWlojmw=",9079347030868986206,8002910852121338350,1054813762950568402,5702223890363116530>()) {
                  case 1382299183:
                     var4 = 8;
                     switch ((int)b.a<"s2xao6tucdg7ry","AbmKKHlZPbPUXL2480TnX+WmYIn3uO7x4vasZrKZ4TI=",-1365474360377454189,-5081023860101647745,5260694670581914913,3941696302042282127>()) {
                        case 1915118282:
                           break label225;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1970943351:
            if (var3.equals(
               (String)b.a<"s1jwaju3ro937o","UKxSKp4hCxJF1COmmDOQBjqRWJf5dabxOhBDPHWg2YVBJSLOyWwz42sxyx+Au50NGYE=",2815794067614453299,-5305547646968169942,4123857058736556506,7811192702076503172>()
            )) {
               label174:
               switch ((int)b.a<"sifltjk23l49n","zI7UL8h3mYmLUCDmMdLI0dHMe4AUl9JYAWNxarri62I=",-343205629616645990,3555223410823589513,5216298710855514639,857037029300885347>()) {
                  case -1014319167:
                     var4 = 6;
                     switch ((int)b.a<"scihiqza39hos","Iz3Ahw6ZDOJOpWvPEhtKi6Ti6zUUR6BGE4dxJhin+58=",-2200326996338665556,-3220814842565500132,4862982256050366153,6248945221502233922>()) {
                        case -578252879:
                           break label174;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 2036739715:
            if (var3.equals(
               (String)b.a<"s33eivoh5j34gn","WhxdXWX+URfbYu2MmgFm34tiiNFIef6Ry0y0Q5z0pxgEEr+AnJYN7xV8CG8=",-7568030155510500942,3654040317981682450,8123031687838768809,-7645842819410775286>()
            )) {
               label201:
               switch ((int)b.a<"s33esn30i1p4fv","ev5qOhUTpNbwYVUBE/Dufvi7uhA/mbk3UUFpqDePW5A=",-9056158066688340409,-7620415813480695584,-4069071218533518486,8939912777512496581>()) {
                  case -327669085:
                     var4 = 10;
                     switch ((int)b.a<"s3l2t400sa5jys","uTIVGndYTdzj17l+0wuMxgZO46J9NrBgyoDMaIXud2w=",-873596505679034420,-8426478657885756974,6531382898033850457,1186458780205097775>()) {
                        case -159621174:
                           break label201;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 2109289781:
            if (var3.equals(
               (String)b.a<"s1ecax8xfbves2","GyPWBtvyqUYl5Z3NSDo6Zy+Yen3oYL8UybPWnO8qpBtoDvVgIlsXZANAbRxyMUvsi7g+F129gu++qIxDBprmL3wmNaTaaw==",8370786821538425158,-880798136581692854,1369029049652946961,3299248299446771753>()
            )) {
               label161:
               switch ((int)b.a<"sq4xzwb0xnl4t","tLtNI/e3pC24ZE4DIMzAxbJyrZuV4sghinNKSu94tyA=",2189070655081827232,1331210182179836232,3585773384141378901,-7942885478178801640>()) {
                  case 602307890:
                     var4 = 9;
                     switch ((int)b.a<"s28h0ilg1pzro5","Qk4NA5oeVQU7satlle6cIHsrc6XaKC0Rulak8aAI6fg=",-9186322711173672030,-5711148553002908461,-2840342168448321013,-5507289163864690997>()) {
                        case -1594030585:
                           break label161;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
      }

      switch (var4) {
         case 0:
         case 1:
            String var15 = (String)b.a<"sn3iu6s7d6bsz","BrUMb9JRGtB1k8pRYzVPY6tdlh14GMKj4671g0u4AkbnNtVnCM8Q09LHuOD/GMkiUV0e39u8lqZYDLCQs0LbPm2E4SVy7NDN",281483563462208219,8117331826110179964,3125583712753355004,-8197553832108868630>();
            switch ((int)b.a<"sg5j7h70pi3w4","JNJH8Laurc6pmI002zo7ySIfRQmGwLa1glMapOmzvNU=",-5268193565168992065,3237911262229770552,7101780705886459016,-3245032383504201267>()) {
               case -144557663:
                  return var15;
               default:
                  throw null;
            }
         case 2:
         case 3:
            String var14 = (String)b.a<"s2lg5j53wcikeb","uu2X4ahhldjTrPTaiXUBbPgrmtoc37gJLxRAmowQpuq1Rn398oNpOe1h3goJtPetgbeRu6JMmFPMul3m5/KIYg==",7727562964998112170,-812250889424538834,-2111643228455941223,7434007050085741099>();
            switch ((int)b.a<"s2vf5tcnd4lubc","r3QwtC+UrlGa4Gp2i7hHtamtLXtOJ36rsEpaAPiPiuE=",-467643245326522419,-8492617794032139666,5475486560519803178,2060576581344209840>()) {
               case 130933402:
                  return var14;
               default:
                  throw null;
            }
         case 4:
         case 5:
            String var13 = (String)b.a<"s1k9xqnfw9ehcm","TtzgpoLT8eMHFkduBnPnTS3XUuhN2wF3xQtEDCTsWG3RNqvIgdR0owJyGSufb9QFqvM7ZyIBjXQ=",-5013537140872109234,29615402573571297,2291593179266035865,620041333646110793>();
            switch ((int)b.a<"s18q89tbn3fi0c","Z0/c+Z0gheuIkU8ZRSQ+X9JIM1zjgm1Wy+hBUU3aNC8=",-7245943987323793425,6670239744442540848,-8290879263627187336,5468201452585660031>()) {
               case 1616411244:
                  return var13;
               default:
                  throw null;
            }
         case 6:
         case 7:
            String var12 = (String)b.a<"s1w2bru0o19ls5","ldQIpr5zmPoN7s0qBiogqd4N+8BEpWj3dJz/mSIMQQpraMz+FeULdHWu5GvzURwCA0IhOIH8O73FoKrsq3I=",-4505910899860552879,594681155622939193,-6616889168540175758,-5156622317395460274>();
            switch ((int)b.a<"s1mq61wh6e9su3","/L5Sm+Mvh9HDmzXy1zx2yKpwm3rDZ8j25bQ63cGoiYc=",-756445570377679933,4090059634219530298,3158352080498366599,2399461300883140221>()) {
               case 39701031:
                  return var12;
               default:
                  throw null;
            }
         case 8:
         case 9:
            String var11 = (String)b.a<"s2fnxagekiwrub","tunxMCfwcoyJmi26oQ1vgJ0Pd3l+Uha0UPjWUXTVgoobGGRo9iPvCck3VW3DHtF7L6Riw0ozFlFu1UWKylc=",-8842447471441803191,-3251347474580231159,6832164727921591149,7264792604456157532>();
            switch ((int)b.a<"s23z8m4hg7esf","YdbtYHq1ew6R+Ne+j66ZxHREm24dFHeb6OgbKvtCF1g=",-6169754961442108439,7439955960365827910,8064297808479121039,-5097501222036885007>()) {
               case -1192489137:
                  return var11;
               default:
                  throw null;
            }
         case 10:
         case 11:
            String var10 = (String)b.a<"s1sjuvjy28qcuu","pIffm3SsygvjC+u25WXeC5AYc2tLg97KpJnlCEy4K14xuTN6XK2n0okzzDvRf24MiYO1drOy3H3wq6uodyGzIw==",-8855995482306774966,641694092045341826,4595857610520646424,-8335241098910824164>();
            switch ((int)b.a<"s1pqy9ylmz6eet","UqUT+AunSUvyp5KXDESNfbw+6MAxT/mNpT+21u7o8Zo=",3244214606550611636,4861128095090529680,-1982080791332299474,-7085947570396935709>()) {
               case 1424126806:
                  return var10;
               default:
                  throw null;
            }
         case 12:
            String var9 = (String)b.a<"skr944v093h70","XAPW74MtqJfXzTCA6tgDONkKPWnA6e4Y84SGoZd5SH1A+DK1WkMdhhbRp4CLLGME0Ox22NT7a41hu/j66DhVJv+AqrrJBA==",-5722947723894694521,-6352352355779036676,9161272848675377622,-3837496928126081125>();
            switch ((int)b.a<"s2wt509li1ljrp","P91VWhSGDlqYWTJ/eAiqLzggCy20MPXFAA/OnQ8x3LA=",-1976056805231882988,7457712863350436755,-5577840911315106632,8547508581599226699>()) {
               case -1662415088:
                  return var9;
               default:
                  throw null;
            }
         case 13:
         case 14:
            String var8 = (String)b.a<"s17ckerkf0ilwj","GhOPvMhb4Pp56jW5d9l3wt8Xqdl0pd7EpNBto3Hj4tHBqlj7pkC+migJC4EBxgIDohwF8PS/boz87zuT+Q7c8g==",8768996336479758221,-9178999513608288380,1878275290778519022,-2220238530521419244>();
            switch ((int)b.a<"s2wjzxpimnskzh","dtMEq06tJc5epHejoAFVDVfMxlGXh5PCaYq2HxK3U5s=",1763499157755717926,9200093676028046873,-1078361360499258236,6789990427339697039>()) {
               case -476332667:
                  return var8;
               default:
                  throw null;
            }
         case 15:
         case 16:
            String var7 = (String)b.a<"s3jhe9702sbme1","uwzSO2j1/6dbndrR3sxflrlXkUWa+azwzg+CfmBBlTN5lABa3Ia7Ppe9tI9rx55wyuMMvWRgPcEh+Y0JdUa7AhN/SBg=",476429665867849343,6061171761193566253,5851930448502718805,4834162702252976164>();
            switch ((int)b.a<"sbob4eu4fw9nb","PEYBOYnHIB6KYPVjvvrEcxuns7uGvSqvVmqWgtDog9A=",-6004613986003349680,-7361579743401250103,7507248208143621754,-5231176515516663611>()) {
               case 1447147326:
                  return var7;
               default:
                  throw null;
            }
         case 17:
            String var6 = (String)b.a<"s3dzn183ckw0zk","SLNeY7TGH/EZ4xlPaPANby8ybOeEHNSbu6e/mfifbG1NEahkvRFUx8hlI3DMXvOCQcaO09vhX8e64Kmz",-1840813548382119442,6706602965822855074,4749207314982805990,-3455025821623453829>();
            switch ((int)b.a<"sv3l84y4dy8xw","DlV90gKk4Xeo3zjwn8BDR9Sweg9TE0gl/i0IHhzCigM=",386721838625109797,1094648117008154962,-3091258546179347080,5031659535115679967>()) {
               case -1214066509:
                  return var6;
               default:
                  throw null;
            }
         case 18:
            String var5 = (String)b.a<"s3uhfacwd4q0tl","wYphFKEpYCaV0A0WgVzOUArVS/z7CY7g2xQktjus+aa1JJS8ZViQBspC/IWdFZhV9vwg/KGQOtMofLagqAYPk98t77D7KyWdc0ZUDg==",-5656496703406750526,3411260926892359713,-8741020600295213764,7801181933849744701>();
            switch ((int)b.a<"s2ncrqofdnjguk","EuV4rHTzGV2U/2kQKXyUNFk3fDql/EwffEza1yLjhdY=",-7172230753535892670,8327036701238582597,5057094907446123920,388607939968392865>()) {
               case 132323076:
                  return var5;
               default:
                  throw null;
            }
         default:
            String var10000 = BuiltInRegistries.ITEM.getKey(var1.asItem()).toString();
            switch ((int)b.a<"s2gpjpew22zij3","g9MReJ7iVyDguGTkfbKYgT/lNSavAcnQOrha3cF14cQ=",6288080996611987434,56598694031729699,-8205275643268943522,5259050762959337569>()) {
               case -1224535858:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   public String bL() {
      return this.a.mI;
   }

   public boolean bM() {
      return this.a.bQ;
   }

   public String bM() {
      return this.a.mJ;
   }

   public String bN() {
      return this.a.mK;
   }

   public String bO() {
      return this.a.mL;
   }

   public String bP() {
      return this.a.mM;
   }

   public String bQ() {
      return this.a.mN;
   }

   public int bl() {
      return this.a.gk;
   }

   public int bm() {
      return this.a.gk;
   }

   public int bn() {
      return this.a.gl;
   }

   public int bo() {
      return this.a.gm;
   }

   public int bp() {
      return this.a.gi;
   }

   public int bq() {
      return this.a.gj;
   }

   public boolean bN() {
      return this.a.bR;
   }

   public int br() {
      return this.a.gt;
   }

   public boolean bO() {
      return this.a.bW;
   }

   public boolean bP() {
      return this.a.ca;
   }

   public boolean bQ() {
      return this.a.bU;
   }

   public boolean bR() {
      return this.a.bV;
   }

   public int bs() {
      return this.a.go;
   }

   public boolean bS() {
      return this.a.bX;
   }

   public boolean bT() {
      return this.a.cd;
   }

   public com.yiyiaddon.e.j.l.a a() {
      return this.a;
   }

   public int bt() {
      return this.a.gr;
   }

   public int bu() {
      return this.a.gs;
   }

   public boolean bU() {
      return this.a.ce;
   }

   public boolean bV() {
      return this.a.cf;
   }

   public List<String> am() {
      return this.a.bf;
   }

   public c a() {
      return this.a;
   }

   public com.yiyiaddon.e.j.f.b a() {
      return this.a;
   }

   public com.yiyiaddon.e.j.j.b a() {
      return this.a;
   }

   public com.yiyiaddon.e.j.g.a a() {
      return this.a;
   }

   public com.yiyiaddon.e.j.d.c a() {
      return this.a;
   }

   private Block a(String var1, boolean var2) {
      if (var1 != null) {
         switch ((int)b.a<"sqpd1sjnyjvn4","DjInFyhJ4QX2xnzWRVr4U5D7xcqhH9V0q+JINvvx9/A=",2693109195883996129,33334894652993844,7135886813785548883,2390292635037738789>()) {
            case -1963772910:
               if (!var1.isBlank()) {
                  if (this.bI()) {
                     switch ((int)b.a<"s1zj8be60ffg76","EEbeyFfS3Y4FDxX7VBdm/kjFTDenVw2kXoTmUlzeWz4=",8745652590288885333,-397505037016972289,470269343895237116,-773348590889683616>()) {
                        case 1743580140:
                           Item var4 = b(var1);
                           if (var4 == null) {
                              switch ((int)b.a<"s36bjyukf1gvrc","Xlc9yfwu68caVH93Seq2Wb1omNjzSgwOw1YkhqVF65c=",-1778430880086203913,-6804533463927311612,-5162807388468300301,-8402134493087298778>()) {
                                 case -1062904824:
                                    Block var5 = Blocks.AIR;
                                    switch ((int)b.a<"szzf79wyk4mx0","S8EKWRrh+E8FTQxAltMmcZv831k54gkVGwUuSa3CB/8=",1917516920439119959,-3627522102726245034,-3601271180107712158,2780786285566898490>()) {
                                       case -2126872689:
                                          return var5;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              Block var6 = Block.byItem(var4);
                              switch ((int)b.a<"s1p31zzi3nywht","1C4jTMRmZiwazAdTxiDsXo3LoiQ2wHE420YwUdJhZrg=",-6840433841032034922,-757681317613689235,1862885824085897593,8312128990880912952>()) {
                                 case -809714141:
                                    return var6;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000;
                     if (var2) {
                        label48:
                        switch ((int)b.a<"s2hecluaqs0soz","OFezQHMqA8YKRIzuEXW0ctR1fR3RMPJpO87WoVUojfM=",-2217867927784993300,-6594052221393503775,3894280885652719084,7018047106458703996>()) {
                           case 1451817840:
                              var10000 = J.get(var1);
                              switch ((int)b.a<"s1wl90h84cgrpr","C7h6u1gyL1L/FjJSUtL/QBsritYpaaAy4ubwEtcH6Wk=",8233659180769659317,-1403507859790982388,-50435652745508516,-7951610333136796370>()) {
                                 case 1604718196:
                                    break label48;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = I.get(var1);
                        switch ((int)b.a<"s1fvx6i6z2kljm","Dyjs+RwqgrlyjklJoeRGcwKHGXy3CNLrSkibw0fQuEc=",-6416412324129097712,6967446403170797631,8139696991797905253,7856276844659084022>()) {
                           case -758399830:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var3 = var10000;
                     if (var3 == null) {
                        switch ((int)b.a<"ss5pu0p1114kc","2pbSSMypNh0VMe7KfvCOfWSBoL6s+GnYkt2bIwQfCH4=",-5359878148877315102,-7837787561502909550,-6231840801655293541,-3251556176940022654>()) {
                           case 1988292712:
                              return Blocks.AIR;
                           default:
                              throw null;
                        }
                     }

                     return a(var3);
                  }
               } else {
                  switch ((int)b.a<"s1pbl4kp0m71ti","tvW04PKp7mUKYAb32YZm1w8ZzgFXI/9AKXplY7BndHo=",-9155500498327204377,-6469224990401737535,-2476510157202507538,-3920780303694524165>()) {
                     case 1349346248:
                        return Blocks.AIR;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return Blocks.AIR;
      }
   }

   public boolean c(String var1, boolean var2) {
      if (var1 != null) {
         switch ((int)b.a<"s1cg8usdlu7k5k","UERYTrKEXuTcEXhwjUCVXE0mT05vQDlNfDloC8llZgY=",-6395588098587054045,2818110304930405202,3146110037354658536,739715304045166018>()) {
            case 1893455441:
               if (!var1.isBlank()) {
                  if (b(var1) == null) {
                     switch ((int)b.a<"sys30mqi8z0bm","QiHO8suxvpTYu0E/ShpreqRQLxlmajx9+AmhxXKMMIQ=",-4958938976774830550,88153772041118653,-5417100436833418676,-2488780102265471572>()) {
                        case -885407614:
                           return true;
                        default:
                           throw null;
                     }
                  } else if (this.bI()) {
                     switch ((int)b.a<"s2dpb1wuz6c901","uQdcKlIBYEvZfE5wwXNvzufxFWj9DNv+/nSjK3m2eT4=",8997163917446299884,-146934703109232740,-3229663421474634577,2511511881879221012>()) {
                        case 261989926:
                           if (var2) {
                              switch ((int)b.a<"sco0a5k205vc3","Z/fXaMwtx7n0JYljGLou7PLoSzwo6oS+TONqrta1HkY=",8477855629803701734,-1802383543344657348,2021411404434900673,6927964871688627967>()) {
                                 case 1954719048:
                                    if (!var1.equals(
                                       (String)b.a<"s3vm36irsql0un","bbUVti9jAvvLtIMoezPKBMUCDPOPR4+PjCjR+1+aeKl8rIXSOTEEQucmhBsu9hdwx2S/GKdCM0fI81lKxzGefbiCpuEnCuuVuQhr0Lk0",8660067117550669922,-6361778220543300984,-7710442031078562216,704516571987372295>()
                                    )) {
                                       switch ((int)b.a<"s9u1ki09aguj5","da6xhTmvn0rgveejfzAWai99t6pm8XhmTMDVujq//Qg=",-1446442842871073683,-4354113529135934975,-6031430385112157068,-4139266762790652526>()) {
                                          case 1876669281:
                                             if (!var1.equals(
                                                (String)b.a<"sk2jft43pvoli","mSOSa3WqXoRB/GTmsqWuSl7Q5SDEUj6JbYuWJFHZLS3UzaaW/1U17giukA2aFJU1Si+OD6pYbX+VxHoePq9EYk9IRb6BBBMHQ+BCJ2sNIZ6Y8Q==",-2073326512259680918,-4783269437395156619,-2435713356032733657,-4908384172244919647>()
                                             )) {
                                                label68:
                                                switch ((int)b.a<"s3o66mdjlcy64c","W8hDcnxy4GpwoyC3tYNAnFGDYiT1FF2WZm/JNJY8l+o=",4607513371687970767,-7584660634466198645,-2259374550744235137,6840810055282323432>()) {
                                                   case 385614804:
                                                      if (!var1.equals(
                                                         (String)b.a<"s3uhfacwd4q0tl","wYphFKEpYCaV0A0WgVzOUArVS/z7CY7g2xQktjus+aa1JJS8ZViQBspC/IWdFZhV9vwg/KGQOtMofLagqAYPk98t77D7KyWdc0ZUDg==",-5656496703406750526,3411260926892359713,-8741020600295213764,7801181933849744701>()
                                                      )) {
                                                         switch ((int)b.a<"s1iea1bxzdz7jd","mzlRc5NGXj92SvM/tclO0JZN8IN6MFSCQS07sEPohRY=",-8340777026852801193,3076883273707411363,8894228684709999641,561956224613089199>()) {
                                                            case -1436492342:
                                                               return false;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      switch ((int)b.a<"s30re6pzcykwyk","tP1Gv3BTiV0JPqurPVmcv6GGxxpHTZ64utSpc4EM/is=",-4502891998609642087,4913339276446688633,3565925543650267743,3205661181411945928>()) {
                                                         case 1551640377:
                                                            break label68;
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

                                    switch ((int)b.a<"s2ujdb287witpp","BG9DynHZKCFvFUE8L+x5l2jx9y779cY/kHoPT8MXngQ=",-5059529512101394643,7124441788712247553,9189031769793350869,9016944658665522438>()) {
                                       case 314312154:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              if (var1.startsWith(
                                 (String)b.a<"s2ses9zeyo19lm","hauw2pUlyfsTSPOYzKNupmrisL7Ng7c2Mib0CvbvDhhjpccK15WOZTUxrd/10qMo",-5000064682901566272,5976521089235583341,-4441528349337633646,-8486173996084656991>()
                              )) {
                                 switch ((int)b.a<"s1yu2s8ucgfs0w","CNwtQ83+PHES7OKENhTRRAEm7yCIpQootG12mIFufQ4=",8735338942871597573,-3426732404339028153,-2869691599264286457,7338239560920251887>()) {
                                    case 359005891:
                                       if (var1.endsWith(
                                          (String)b.a<"s2qceeg9xjihso","d+URXLkRIRETMzwqOGrA2/x4PrSoUAMUIW2ush04uoJb3kVl",-3529376989139755155,4829571212643619987,-5940073486101278557,8794515326123230234>()
                                       )) {
                                          switch ((int)b.a<"s3s4ia25dcsaj5","AkPJm8oJc7BA4v3sUybMAshnNOprawREFbDzxN0vEic=",-1812456227792589360,-6288686167519333619,-5896911362937587,2720475325558564226>()) {
                                             case 978520651:
                                                if (!var1.contains(
                                                   (String)b.a<"s1jdxpc022wxp","Eu6zzkqe7GhpeDcD4CDH435E7TnuNzvIsWBtzdASm/g6omKFGD6H8LxdwNiVzg==",-5790588102559185898,-8252033684538269920,2831285008260438205,7438567212164169851>()
                                                )) {
                                                   switch ((int)b.a<"s1p7y1859o29pp","oPzhWcfyUDZJ6yh4rQb9B43aLPRIt+02nE5RTaYE3xE=",7890351397569084848,-2480759784227369011,3449895438316135666,-4179067391268905871>()) {
                                                      case 1506327730:
                                                         if (!var1.contains(
                                                            (String)b.a<"s2see7ypumwvg7","djPA+9WYHR/m3WDW47zSYE5XRUvB5k+F2kKX/8Ct5omQ9NPGPz1f3A==",3174404254353320222,7421059515476963685,7792337310962896132,3775749986520252746>()
                                                         )) {
                                                            switch ((int)b.a<"s1om7f01vj3npo","ZiVcHVdx7LzkYZcPq9RlmrkUw9kUOULuewzTc4ZFMQE=",-3448693090355385842,-100973861903890640,-1504826962585992434,-7437957904363928659>()) {
                                                               case -769336966:
                                                                  switch ((int)b.a<"s2w5o7gut54e80","C7ToM7ZCeiiZZOp8E0Vo5qExboPl9s+rZpckKCQA6Bw=",-5086938570178859458,-5032417185987761890,-5068494561704742470,1317444324301135348>()) {
                                                                     case -1239307314:
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
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)b.a<"s3c7wx63nd6anc","WKL8r+CMEuBx2XQGFVGOGcVSXre0WDfFeWKvxJuUqgo=",1649440193423747922,5915175911017002432,7979879695780497764,2984161193697879960>()) {
                                 case 1334916555:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else if (var2) {
                     switch ((int)b.a<"s2m5sgfby24z2t","lr6nYaj7qFBf1zqzztCjHI5tXi2kvbxdhmpatgGdoW8=",6564391381798803721,2928655000383977009,9017684151488143356,1753128756500621100>()) {
                        case -845217876:
                           Map var10000 = J;
                           switch ((int)b.a<"s1rej8twfdo8e7","i1dDqKcy/rrICudg9frF5Azlg8G82gbQWFoQkpIk7CU=",5901299808884862330,-660314104063735455,-2259608030824559617,-6033408842350358254>()) {
                              case 249390359:
                                 return var10000.containsKey(var1);
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     Map var5 = I;
                     switch ((int)b.a<"s2gdgctgvzg7wp","kax5LnBvau5NnNV5pIG2nqI59VlzgFdKUqm4p5DVXjA=",-6343484922087191601,530430365744656143,-453502946644167820,-8021989552963737484>()) {
                        case -878182261:
                           return var5.containsKey(var1);
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)b.a<"s1hpv8od338dv1","rBCL1zhZjBnWFcUbN0PbPuHy9p7YEP3r054lmLZcoX0=",-508057359802884457,-7853080732120352552,4352916905488078655,-4299575685398527382>()) {
                     case 1809401496:
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

   public void eB() {
      boolean var1 = this.bI();
      if (!this.a.mF.isBlank()) {
         label23:
         switch ((int)b.a<"s9ig1ivwej2dc","9l0c3jadCFarPkAtsUxVAkvTDAvbPHNs/Mi1RvQ3DDQ=",-6258755147529965800,-180667069267707768,-1949651387442344590,1579634611063200270>()) {
            case -2135045451:
               this.a.mF = this.a(this.a.mF, var1, false);
               switch ((int)b.a<"s22nfvtp5nvi44","zUHh56W6mfDHcgOLxVFzxZk4oBlSwAao7KWXM7h6fXA=",-3066012988870544942,-6907341590760391921,-7472371230887731375,3807936648667413027>()) {
                  case -380771207:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (!this.a.mG.isBlank()) {
         switch ((int)b.a<"s2y5qkku3nme9u","eHa+xCrCPzmzLFv86zQuQSc+mM7ZxPHW8HEe6CqYHG4=",-1715041374493813881,3783117017073651400,-11810517304980004,-5383881824000837493>()) {
            case -1852080496:
               this.a.mG = this.a(this.a.mG, var1, true);
               switch ((int)b.a<"s2ix9g47bgy57u","bd8lFqg5X/mw5D4x3d0Uq+rrqfN6B4lsWqmh+Rh25uk=",-8663640311262695712,-127757468152944669,4346221416568201619,421715740352602165>()) {
                  case -1921599069:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private String a(String var1, boolean var2, boolean var3) {
      Map var10000;
      if (var3) {
         label80:
         switch ((int)b.a<"s3vh51azaq5xw8","BeFOnoy2eA/ZjkZ6mrp7y4nWhre7Fc+p0nzs7ZJw74s=",5596476753273478731,-3328536911881814488,-5547149485283215640,-5203534754717344858>()) {
            case 1847279344:
               var10000 = J;
               switch ((int)b.a<"sydzp30cmgnvb","m9JaRJPbG6nUH+Pv+tFNkLlJg93ByB7K1N67ptZi3a0=",-7760531220606794843,7427266029693115438,2844684296548468531,6498447784833715645>()) {
                  case -1773811466:
                     break label80;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = I;
         switch ((int)b.a<"s35c3yiow16cry","clRXcSi/O7EBnlDKcWNbFc0quIn/TR+2qxa27LrKmqw=",4521054446702558084,-2700620300255453911,1764946369040804152,2636702768570315188>()) {
            case 1816090548:
               break;
            default:
               throw null;
         }
      }

      Map var4 = var10000;
      if (var2) {
         switch ((int)b.a<"sy02kxruck5d2","aT4gQ4bxabl9Lplbhe8cU93E21qK0qy3IBILNqUXAkE=",8950786512465555947,-1368535837234167667,2785662330272127110,-3086780185002748289>()) {
            case 974685382:
               String var10 = (String)var4.get(var1);
               if (var10 == null) {
                  switch ((int)b.a<"s26v1v8frta9sh","4hIndKuc6kWVOpYrp711npSYK6yJwEgYuJdoppdxvB0=",-1960080080859951814,-5038578508292751568,3248554770046725656,-5823689974352038412>()) {
                     case 1478580448:
                        return var1;
                     default:
                        throw null;
                  }
               } else {
                  Item var11 = a(var10).asItem();
                  if (var11 == Items.AIR) {
                     switch ((int)b.a<"s2jyzw5gjgjakw","RnwphlLVQNYWHVEGeIr9qRcU3PLMb/aw2causFHJvEE=",-5458853849537472084,7141944888062051365,-4522756910638050187,-2100911962209642798>()) {
                        case 199655826:
                           switch ((int)b.a<"scfdw6e4yfqg5","qOhlb+GbRl0MsCUpj5P28WGZqsBVm69ga8f6TB2RLl8=",9163699187864227319,-3146366178070072362,595200863964570547,376624556503398505>()) {
                              case 372093560:
                                 return var1;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var12 = BuiltInRegistries.ITEM.getKey(var11).toString();
                     switch ((int)b.a<"s2b2l40ax2as9x","iPIKRQroQS0JDELI7W9wzNlNYds58H1SRslv97gP2qg=",-4474428216874656215,-4937741345805686408,-6740063767326022010,7388966753747554693>()) {
                        case -2092829571:
                           return var12;
                        default:
                           throw null;
                     }
                  }
               }
            default:
               throw null;
         }
      } else {
         Item var5 = b(var1);
         if (var5 == null) {
            switch ((int)b.a<"s2q4foq6gqdh78","PMiMFU+pmSu4U/LhZlKPvWwdUA8RdmvtRLTbZsy/nv4=",-8305819716193991613,-3143052040372417957,-2042926398253272119,4210240630053061578>()) {
               case 2087447374:
                  return var1;
               default:
                  throw null;
            }
         } else {
            Block var6 = Block.byItem(var5);
            if (var6 == Blocks.AIR) {
               switch ((int)b.a<"s2ymac8bb2esc5","FDKa9WBJsrZANE8nfRxcuquo4JJGqy8sbbtNocbnQx4=",-1514931350211681240,-8537772148061674227,-4057357558986285237,-804893678515216712>()) {
                  case -2087576613:
                     return var1;
                  default:
                     throw null;
               }
            } else {
               String var7 = BuiltInRegistries.BLOCK.getKey(var6).toString();
               Iterator var8 = var4.entrySet().iterator();
               switch ((int)b.a<"s2ccm9dqbi2cg","LLGyIkOu43D3qF30O1n79fbwwrJm0bXty317Tn8hYWk=",5301291982351002859,-4383663155855913603,1518589595257845197,-1773796988109483879>()) {
                  case 250330876:
                     while (var8.hasNext()) {
                        switch ((int)b.a<"s2j5al4778lo90","OGDiYwv9P4AXsfQPYe+9tvNpeVmD9UH1/FSM9XYD06s=",3803678826032729697,8677053332778434339,-5560053760720927835,-2158910663113378761>()) {
                           case 1995699428:
                              Entry var9 = (Entry)var8.next();
                              if (((String)var9.getValue()).equals(var7)) {
                                 switch ((int)b.a<"scl07k0b50y8","ub0ZiAnQMaSr3aIDWGwtLBAFL/aRPtt2vvmJW+sHw8I=",5512461239071105529,6444705195482774955,-139982953186109108,4237922081507188103>()) {
                                    case -1437133798:
                                       return (String)var9.getKey();
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)b.a<"sbbtoni14chkq","1gd1BPNwmrdzdDorD5EWfhLoj6K1WIKVq1Elr9z9Ru8=",1416138364540453752,-8308552256888393278,1662782596169982498,-5895849082419758395>()) {
                                 case 954688662:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return var1;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private static Item b(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)b.a<"s39b5k4xehzocp","0xiTpegU6lZAPXHl1pKIa4sb4qB3vS6MZCFME2ABER0=",-3431996598868880961,3268141653481628868,3897721448603042223,-4651189471581631380>()) {
            case -1624707406:
               return null;
            default:
               throw null;
         }
      } else {
         Item var2 = BuiltInRegistries.ITEM.getValue(var1);
         if (var2 != null) {
            label27:
            switch ((int)b.a<"s120hmhttrm6aq","yPh/rGGkE/LCzc6zuYmSbSzigybhpEzJbXgOIQy986E=",-4473640306024057322,7721336230882866769,7033272218329898344,-5008012601716039400>()) {
               case 417272575:
                  if (var2 != Items.AIR) {
                     switch ((int)b.a<"s2suq1tgkw8rp5","/FqVtL6CNVxtISk9/Y2Ksps/a/A2Hsea0AwB62lRRPE=",-3681712976190171053,-5824181161877144235,3440233836654812549,1094429479775375004>()) {
                        case 1871343058:
                           return var2;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)b.a<"s2tnf79smi3ydx","YUhhvA8AZLn3kfeTbQ3M+nAefKtUx4TJxD/kJq+7g8Q=",-1897267181465547158,6788087807904167988,-8732400279124870705,-7765429381556042695>()) {
                     case -139265356:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)b.a<"s3lptimkxz0z0x","lNHeBRhqh7OSDN+YPPFp3YS7qKLfGKuDAOy9tajTFOY=",3602232998382159034,-707498789122023255,-6766781167397621629,4031841965585579649>()) {
            case -798919911:
               return null;
            default:
               throw null;
         }
      }
   }

   private static Block a(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)b.a<"s1yrsh3pizz01x","9G6Qz5NlE73byo7F9vaEpr6Qg6wLzSvFAz5VZVLxzJU=",4173570783442870835,-3301385281694035326,2303592744771722327,-4775526632161360533>()) {
            case -768558657:
               return Blocks.AIR;
            default:
               throw null;
         }
      } else {
         Block var2 = BuiltInRegistries.BLOCK.getValue(var1);
         if (var2 == null) {
            switch ((int)b.a<"s1oge261be2upy","siuT+B9+9z1brJOcHrbNOIivVJlufnI8zNlTnBf66UY=",1428410174325579640,-5064897641592742703,-8327794343506893589,-3604156703980874299>()) {
               case 1635115167:
                  Block var10000 = Blocks.AIR;
                  switch ((int)b.a<"s2337jwn470znv","9VMY5AvUIKOejJqVoiO8Op3LrCHLT7Apkq8KbbI2vCQ=",6295622427455229131,7168378069492231953,-6015591056061490360,1937754310809119248>()) {
                     case -1151742718:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)b.a<"s1eedolfit6fe3","QTZWCGKkR/NlK6DQPfYYYzgYeUh3TlXjj3yiU4BuznU=",6775477863364859071,9215645251053660465,-8859729966017718347,-3164148498811638179>()) {
               case -2099249419:
                  return var2;
               default:
                  throw null;
            }
         }
      }
   }

   private void S() {
      a(this.o, this.a.gB);
      a(this.p, this.a.gC);
      a(this.q, this.a.gD);
   }

   public void T() {
      int var1 = a(this.o);
      int var2 = a(this.p);
      int var3 = a(this.q);
      if (var1 == this.a.gB) {
         switch ((int)b.a<"s236znn67beua2","lWR0O6l8ed4Bnyjj8Ft+Ao41f5V3MDSdPUvzmAGoN2Q=",7957842025056818674,6767391700290998202,-110550697866512331,2208578866810395225>()) {
            case 1509639149:
               if (var2 == this.a.gC) {
                  switch ((int)b.a<"s37gxqz9zw3u3f","3nBL5TzLykaScuUxFvT7n4rdncsOHEFEFqYv4oW4MiQ=",2973753722207630457,-6706250064003181959,-4253456896157508295,3160926575244615628>()) {
                     case 829154825:
                        if (var3 == this.a.gD) {
                           switch ((int)b.a<"suscvb6d7n5z3","TzF2SsMYJpCcMB6+0NI5U9diSN7QwXjDi8TrLCNbhUo=",-8557455594189527796,-454047986046296368,6310895588659722767,-155909440747589379>()) {
                              case -1731062352:
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

      this.a.gB = var1;
      this.a.gC = var2;
      this.a.gD = var3;
      this.L();
   }

   private static void a(d var0, int var1) {
      var0.b(var1 & 16777215);
      var0.c(var1 >>> 24 & 0xFF);
   }

   private static int a(d var0) {
      return (var0.ei() & 0xFF) << 24 | var0.eh() & 16777215;
   }

   private boolean a(ItemStack var1, ResourceKey<Enchantment> var2) {
      if (!var1.isEmpty() && this.D.level != null) {
         ItemEnchantments var3 = var1.get(DataComponents.ENCHANTMENTS);
         if (var3 != null && !var3.isEmpty()) {
            try {
               Registry var4 = this.D.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
               Reference var5 = (Reference)var4.get(var2).orElse(null);
               return var5 != null && var3.getLevel(var5) > 0;
            } catch (Exception var6) {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void eC() {
      this.et();
      this.a.C();
      this.bg = S();
   }

   private void eD() {
      if (S().equals(this.bg)) {
         switch ((int)b.a<"s12g0kio9bkzt8","MgQcuqJrv48PnASmlaCwS2TEioq2wPawlylgO3ual6g=",-3338751902045974551,-6632956537872842942,-7414095005033452534,1667945607633312141>()) {
            case -1412928071:
               return;
            default:
               throw null;
         }
      } else {
         this.eC();
      }
   }

   private static String S() {
      return com.yiyiaddon.i.g.c.fG() + com.yiyiaddon.i.g.c.bU();
   }
}
