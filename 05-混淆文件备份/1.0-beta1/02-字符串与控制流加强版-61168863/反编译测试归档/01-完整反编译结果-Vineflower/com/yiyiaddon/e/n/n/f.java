package com.yiyiaddon.e.n.n;

import com.yiyiaddon.d.a.h;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.FontDescription.Resource;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemLore;

public final class f {
   private static final String tU = (String)com.yiyiaddon.m.b.a<"s12s2kepeood2t","Kheu4YqvJVqjunPrjy/7gU1HEfE0LuCoXnCxmp4htuY93/TPiavXEg1ObWD5s27Y4CuzBSz8BlOhXK+PZIXczoLsO2oeF/pO",-7151558993891067593,83060713557627712,3989519410531411229,1018672499613897763>();
   private static final com.yiyiaddon.e.n.n.f a = new com.yiyiaddon.e.n.n.f();
   private static final String[] W = new String[]{
      (String)com.yiyiaddon.m.b.a<"s2bsmf7cyridj7","YaqbYmwyvps6JJmtoX3ZkXsw+AiLUMciQprkFsbMBxuBeJDy",6763600331611011464,3717653938723601344,8384891226640444029,-7340477580737700147>(),
      (String)com.yiyiaddon.m.b.a<"s33wgiupn6h4l3","kvVquLhd2auHjXqK5Ad9OteYgWjU6jI3tWOXQiN3HHXT/D5e",-8530229411861544975,4561899720975783237,5155006867723979960,-2229206909904237307>(),
      (String)com.yiyiaddon.m.b.a<"spwpqw9jhghso","UA3RSywwZmeH34QyTMVsDCENK43+yqn643SUWdTQE7T9Fs5C",1180877767276886758,2365223984857517676,-7378855972658709753,-1388385147028081648>(),
      (String)com.yiyiaddon.m.b.a<"s20pwqycq3hbxq","baUSaq1ORM9RnxAnN28eqIj1QlY0HfGEkWAxPq9SRhUlqFng",-6642724155095301558,8875851621927690656,4280905434074099095,-2655602864435273838>(),
      (String)com.yiyiaddon.m.b.a<"s76k6ab0stnsh","xxAQOk66h4NxHGWDUCtNE17zqBLINflalym9qbmpCdU=",650979313922611054,5327617517843518819,-5831954693249451173,1917914053630203649>(),
      (String)com.yiyiaddon.m.b.a<"s1gs02c7yvxkal","bK+qwYWCl+WexS5Zd0BrocNO3ySuXHNYlut6Dzr3S7QgWKO6mAL1Fg==",3895730596753925082,7677451868666332855,5266959011131442183,-4100286269313424410>()
   };
   private static final long H = 5000L;
   private final Map<String, com.yiyiaddon.e.n.n.f.g> ak = new HashMap<>();
   private final Set<String> N = new LinkedHashSet<>();
   private com.yiyiaddon.e.n.n.f.e a = new com.yiyiaddon.e.n.n.f.e(null, null, Set.of(), null, null, 0L);
   private volatile com.yiyiaddon.e.n.n.d c = com.yiyiaddon.e.n.n.d.a();
   private volatile String tV;
   private volatile String tW;
   private volatile String tX;
   private volatile Component b;
   private volatile List<com.yiyiaddon.e.n.n.f.a> bR = List.of();
   private boolean dM;
   private boolean h;
   private volatile long I;

   private f() {
   }

   public static synchronized void init() {
      if (!a.h) {
         a.h = true;
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s12s2kepeood2t","Kheu4YqvJVqjunPrjy/7gU1HEfE0LuCoXnCxmp4htuY93/TPiavXEg1ObWD5s27Y4CuzBSz8BlOhXK+PZIXczoLsO2oeF/pO",-7151558993891067593,83060713557627712,3989519410531411229,1018672499613897763>(),
            com.yiyiaddon.d.a.c.SERVER_TEXT,
            a::j
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s12s2kepeood2t","Kheu4YqvJVqjunPrjy/7gU1HEfE0LuCoXnCxmp4htuY93/TPiavXEg1ObWD5s27Y4CuzBSz8BlOhXK+PZIXczoLsO2oeF/pO",-7151558993891067593,83060713557627712,3989519410531411229,1018672499613897763>(),
            com.yiyiaddon.d.a.c.JOIN_SERVER,
            var0 -> a.gO()
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s12s2kepeood2t","Kheu4YqvJVqjunPrjy/7gU1HEfE0LuCoXnCxmp4htuY93/TPiavXEg1ObWD5s27Y4CuzBSz8BlOhXK+PZIXczoLsO2oeF/pO",-7151558993891067593,83060713557627712,3989519410531411229,1018672499613897763>(),
            com.yiyiaddon.d.a.c.DISCONNECT,
            var0 -> a.gO()
         );
         com.yiyiaddon.k.e.c.l(a::ge);
         com.yiyiaddon.k.e.c.m(a::gO);
         com.yiyiaddon.e.n.n.c.init();
      }
   }

   public static com.yiyiaddon.e.n.n.f a() {
      return a;
   }

   public synchronized com.yiyiaddon.e.n.n.f.e a() {
      return this.a;
   }

   public String ei() {
      return this.tV;
   }

   public String ej() {
      return this.tW;
   }

   public String ek() {
      return this.tX;
   }

   public List<com.yiyiaddon.e.n.n.f.a> ba() {
      return this.bR;
   }

   public synchronized void gN() {
      com.yiyiaddon.e.n.n.d.cy();
      this.c = com.yiyiaddon.e.n.n.d.b();
      Component var1 = this.b;
      if (var1 != null) {
         this.a(var1, this.tV, false);
      }
   }

   public synchronized long o() {
      return this.a.o();
   }

   public synchronized com.yiyiaddon.e.n.n.f.c a(com.yiyiaddon.e.n.i.a var1, ItemStack var2) {
      this.c = com.yiyiaddon.e.n.n.d.b();
      if (var1 != null && this.a.dz()) {
         com.yiyiaddon.e.n.n.f.g var3 = var2 != null && !var2.isEmpty() ? this.a(var1, var2) : this.a(var1);
         if (!var3.dz()) {
            return com.yiyiaddon.e.n.n.f.c.UNKNOWN;
         }

         for (com.yiyiaddon.e.n.n.f.f var5 : this.a.u()) {
            for (com.yiyiaddon.e.n.n.f.f var7 : var3.v()) {
               if (var5.L().equals(var7.L())) {
                  return com.yiyiaddon.e.n.n.f.c.ALLOWED;
               }
            }
         }

         Set var8 = a(this.a.u());
         Set var9 = a(var3.v());
         if (!var8.isEmpty() && !var9.isEmpty()) {
            return Collections.disjoint(var8, var9) ? com.yiyiaddon.e.n.n.f.c.DISALLOWED : com.yiyiaddon.e.n.n.f.c.ALLOWED;
         } else {
            return com.yiyiaddon.e.n.n.f.c.UNKNOWN;
         }
      } else {
         return com.yiyiaddon.e.n.n.f.c.UNKNOWN;
      }
   }

   private com.yiyiaddon.e.n.n.f.g a(com.yiyiaddon.e.n.i.a var1) {
      String var2 = this.a.bT() + this.a.dn() + var1.dk();
      Iterator var3 = this.ak.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sj64u112sh010","psKf+GPdWOOSBnMIoke4VN9AtZ/TGjFz6qQ/kGFfl6w=",3982426810585423093,-4440781683914467709,-3505414634687857670,1157007879915767410>()) {
         case 1093575060:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s23zw03xtjox4s","sa19BwBAhkGd7pG8Cbj3N2aEXDSZn+leQbivWnVYjoE=",3383781714339264956,3968599108965039699,7111002952312325432,3740919082341554529>()) {
                  case -1697420305:
                     Entry var4 = (Entry)var3.next();
                     if (((String)var4.getKey()).startsWith(var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"smrq9i5kydxjk","/CGna3IRzizXDREyuyI4UGbhhe8bl6Nkbt4rsp9FbW4=",4266830655828803782,4606625917518043818,-7070224213109359736,594330407759094298>()) {
                           case -2021626159:
                              return (com.yiyiaddon.e.n.n.f.g)var4.getValue();
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s38hx0u4fwv3rr","th2NYj4UNiYGgigKru4s7TqY91AUED3wB9y7C7LUSPE=",627929437801485471,6848854581778061087,-7173211578661011433,6303770338646049914>()) {
                        case -798079219:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return new com.yiyiaddon.e.n.n.f.g(
               Set.of(),
               (String)com.yiyiaddon.m.b.a<"s1p0mezo7d9c9f","MDd8yNtDjISbF4wIz8VMT79bz7rgYTrQWxtZFy7csHHmXkBt+ekGcfOKT2iD6YSkBJJjPAxzk2rn9Q==",7450377207892517651,2855558988845254245,3463346716520503554,-4608894141655924820>()
            );
         default:
            throw null;
      }
   }

   private static Set<com.yiyiaddon.e.n.n.f.d> a(Set<com.yiyiaddon.e.n.n.f.f> var0) {
      LinkedHashSet var1 = new LinkedHashSet();
      Iterator var2 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3rz5iwguve62g","uJ+9tOvWskJ98U8IaIVnYgGS/qdnJ+OXwuVaWR8l+Zk=",-8975881641297156549,6349242828865299823,-6270033678445140282,-5277834766279182462>()) {
         case 1203563174:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2j7vyi2rdyor6","t7254su2T/Hp62aOqEp3wL1MnpC7xiIYzft1MQQeHlI=",2579057952214038686,-5416478515085242557,1473928210013774637,3669121608532561904>()) {
                  case 1450955148:
                     com.yiyiaddon.e.n.n.f.f var3 = (com.yiyiaddon.e.n.n.f.f)var2.next();
                     if (var3.a() != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"sxit5baaraca2","I9UPvhkmrjnQgT8Y9tcb+azecSPFbIk0DaR0bdc5hPE=",-4233104263832259344,-643997830249827835,9147031071301168541,-8990524756301419320>()) {
                           case -1946339666:
                              var1.add(var3.a());
                              switch ((int)com.yiyiaddon.m.b.a<"s1dxmnd1glo7e0","hKOhBsQ6qu4cpnk/56yCxsIFxhDPBzBzvi7/fR/isVo=",-4950076244667977542,4750491136734796481,-7871886625154171589,9101399225890651482>()) {
                                 case 2040639633:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s25u2y8qro5r4a","0zYY8dPlaZMQft2edKwR4TO3fU1OlhtMwZGfK47Nb/Q=",3024348716681421423,-2363936079480372973,8393052402189601737,7828844633469897796>()) {
                        case -556922013:
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

   public synchronized com.yiyiaddon.e.n.n.f.g a(com.yiyiaddon.e.n.i.a var1, ItemStack var2) {
      this.c = com.yiyiaddon.e.n.n.d.b();
      String var3 = this.a.bT();
      String var4 = this.a.dn();
      String var5 = com.yiyiaddon.e.n.p.a.d(var2);
      String var6 = var3 + var4 + var1.dk() + var5;
      com.yiyiaddon.e.n.n.f.g var7 = this.ak.get(var6);
      if (var7 != null) {
         return var7;
      }

      LinkedHashSet var8 = new LinkedHashSet();
      ItemLore var9 = var2.get(DataComponents.LORE);
      if (var9 != null) {
         for (Component var11 : var9.lines()) {
            var8.addAll(this.a(var11));
         }
      }

      Minecraft var14 = Minecraft.getInstance();
      if (var14.player != null) {
         for (Component var13 : var2.getTooltipLines(TooltipContext.of(var14.level), var14.player, TooltipFlag.NORMAL)) {
            var8.addAll(this.a(var13));
         }
      }

      com.yiyiaddon.e.n.n.f.g var16 = new com.yiyiaddon.e.n.n.f.g(
         var8,
         var8.isEmpty()
            ? (String)com.yiyiaddon.m.b.a<"s2vw0i1r2w12sk","I6ntN0OSPvUFU8gL8y/paro67MQgqS/UKHmactNYALzWBfjeseOH2NZ+lmSJZyeKKq5aQx62caHkYq62FdWmyQUQ",-8628427582119791305,6918298949251434700,1089392411791630049,-4499660621172410422>()
            : (String)com.yiyiaddon.m.b.a<"s10vywpmp89868","B62Kl79NFnxXHXoUYaTKSEJ6s/Zzt9dx3dNqU3bu9HXuA7eQyrTJO+0yl8D0f9jQuKx4X9NerxXZzpve+NfRV9vWXau1nJAHR0BhtusSVOkaMSjnPX8Za1L+g4SXgk2+vxE=",8895833122806357593,-7952251641923250395,404402464386007264,3097247100026796371>()
      );
      this.ak.put(var6, var16);
      return var16;
   }

   private synchronized void ge() {
      String var1 = com.yiyiaddon.k.e.c.bT();
      String var2 = com.yiyiaddon.k.e.c.dn();
      if (Objects.equals(this.a.bT(), var1) && (this.a.dn() == null || Objects.equals(this.a.dn(), var2))) {
         this.a = new com.yiyiaddon.e.n.n.f.e(var1, var2, this.a.u(), this.a.dG(), this.a.en(), this.a.o());
      } else {
         long var3 = this.a.o() + 1L;
         this.a = new com.yiyiaddon.e.n.n.f.e(var1, var2, Set.of(), null, null, var3);
         this.I = 0L;
      }

      this.ak.clear();
      this.c = com.yiyiaddon.e.n.n.d.b();
      if (this.b != null) {
         this.b(this.b, this.tV);
      }
   }

   private synchronized void gO() {
      this.a = new com.yiyiaddon.e.n.n.f.e(null, null, Set.of(), null, null, this.a.o() + 1L);
      this.ak.clear();
      this.N.clear();
      this.dM = false;
      this.I = 0L;
      this.b = null;
      this.tV = null;
      this.tW = null;
      this.tX = null;
      this.bR = List.of();
      this.c = com.yiyiaddon.e.n.n.d.a();
   }

   public synchronized void a(Component var1, String var2) {
      if (var1 != null) {
         if (System.currentTimeMillis() - this.I >= 5000L) {
            this.c = com.yiyiaddon.e.n.n.d.b();
            this.a(var1, var2, false);
         }
      }
   }

   private synchronized void j(com.yiyiaddon.d.a.a var1) {
      h var2 = var1.a();
      if (var2 != null && var2.a() != null) {
         Component var3 = var2.a();
         String var4 = var2.r() == null
            ? (String)com.yiyiaddon.m.b.a<"s3pizpky6mg9be","wNYGktyRVWdi4dDemYbk4O3hrW0WjPUc4o/Piw==",-510486590335624637,4664701912018333127,6599201736554935114,-9205879572517995879>()
            : var2.r();
         switch (var2.o()) {
            case (String)com.yiyiaddon.m.b.a<"s28bb1gobe9h4d","yWUPucwe1G++qLJ1T0h3BuVw6nFNw+OwRWXQiQX7L5o4SCtScasYiSbZeFvNWmTr",1736147727294611870,-2282042283930324332,-2752442241753617918,5316386430242303665>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s1j44d9cn2lul9","1chkj30R0owSCzxTSV2FYLbthdLNmhe0napWdoVMX/1lFw==",6730859903577921111,-4673912947590467523,-2842334506417334503,2598936844778842522>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s3tmv2158q2gqn","GI4HEPrZ1QhWszvCNsLb5Y+TZg+zpSuCXwQe4ItVFpGAdwPKS7w=",-3238312586713120483,6304971094503356236,-4069249802998909681,2906092598733148737>():
               this.dM = b(var3) && !this.a(var3);
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s7t6s1hpu4mqz","HLl/kaxns+TOa1JEQX878HmZbgaKiUo2aDvKQYbv2WI=",4796292875483382279,2107003070005440415,922116694593073443,-6308260198234270557>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"skypacpmid0qw","EfIaLmJCUWra+rgRsiExQloI1OjGpyaCdXIkXLHkLIfiBzSys/+Rxrv35d8=",3532930356350274927,4950750178720048388,-8260650360552879551,5002060798813438872>():
               if (this.dM) {
                  this.c(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"smiptt29lrnlf","dE8iaWsnDXwu0qsPcQEcc3c7ZCrF+JRb31ln2tKJFDjgisyyiQLDSrbkp9M=",-8631194274180208927,-6571762497485662820,2086213703131559559,992517425128286013>()
                  );
               } else {
                  this.b(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"s2207i5ise862q","7ZloY8jCPWfn0JdaBpKJ+E1B6FYaVQrQuX9vtb4Y2vC+jA==",-7752579753183700778,2062588593641710090,515394551965322862,5772968720841642983>()
                  );
               }

               this.dM = false;
               break;
            case (String)com.yiyiaddon.m.b.a<"sukwk9s1busbr","HH8IBhp1y1I5uZyuTxlmW8YZyqqpEWKTUPbz5ZG0+HFt9JIR",-6878087112186712550,-6107795137895234352,4720601451452655628,-2974298251259386290>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"sdokbhjriomos","Yddkql72+ufzl8rQsN1EBZMiRRfTGMeYixG4ZEy5kWHYsA==",-8163350959209416266,-3901988725456591098,-5433672693181492609,-6174467029325374465>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s25pby4ffw7ggz","jjknBQ/fSQoerqybL34JSz1JjMNwYL4jqSbHEI0OClQoF9kJUgLzV7Rmzp6rkLdRrJ7oAQ==",-5530569989301842257,-4647462121289823395,-5607409579616104837,1177593583172078715>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s47kdco3rr42i","gFqEO1y1BmWgj/VLG9OUSKcaUXgO2PTgvjP9OjusKsepKTYuwd0=",3693379897005545239,-8329678368874112179,-1775339116269864437,3597125977309339546>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s189kf806q5c9y","RK16Ow/Y4wPzGIGcX5zFShNYh1gmtkth5YCzqH47C2EmLB7z4CigukO1ihk824ZG4Fo+MaNK/s8pXg==",-2014033999448660806,-6774888724172538417,-5482785100583472499,-1190491405837880605>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"sli0t9ko3u0v1","MCPXQgv6yCWznrA7LzVOJJIljiLVBVc32Fl5YnwqfuET++uKH4mU/m3eUGo=",-3096693567207829675,5043687009503804886,-830162212598575047,3761904134963813855>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s20q1ngm3nw55v","m0Oml/Mj/b2UDKeFqGk3tQoBzPeSBI7+FPBZ1CFbvEsiD+fHgTlI9oYANiVCW94HCQfKwrlC/Vzd6g==",8848987777252797470,-4996410466072143771,-8015575128739222651,-5866769072578147981>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"sbyik37gi5s0o","E+2GAj1Lnv4+G58EPtKhynD9s8KBNVJHdh+hxA34Bf2oN9DNEn2g+eaQm9I=",-1547764711624072660,-8243352958675085878,6529476144462179287,-6970204489080342324>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s228ei9vexf6yo","+ZPskvEsQwk/F9slMwVkFqVvg2JtBq/aumA5wmwnQkE8jwQD4+12bi/1fU5E8P72+jaWf7LZWLgvQg==",-3391508033735782769,1049604995519262906,3289429158243641691,6158331600924051858>():
               if (b(var3)) {
                  this.N.add(var4);
               } else {
                  this.N.remove(var4);
               }

               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"semxped0elgxz","BxMovr2zlgYM7/TYAScGUYrKRN6fvfL1lDW8dYKkAChcS85h3RlQaEGP",7728811310503569375,-7890902519418896874,1778583210978463894,7575233522617597148>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s1wbkqrf9onkl7","1fFoG8i0CRinkasqQXamdJOBSbM1GAKRoS1myc2JZLM1nqfiU2DJCkSb/gz++CTlmaNajOsQ9RpQwCKTEDM=",2403880242148335617,6026849250828683899,-585953202712186759,6840310276661937558>():
               this.N.remove(var4);
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"semxped0elgxz","BxMovr2zlgYM7/TYAScGUYrKRN6fvfL1lDW8dYKkAChcS85h3RlQaEGP",7728811310503569375,-7890902519418896874,1778583210978463894,7575233522617597148>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s2lfknxab1di5r","G+8/xNbCRnluolmdlsKGi24GTgYVOMA03Y+gkXV3zye3SeMOMa+ussDsWXqTxM9Q35vRdaw+",-4974442814241458585,2143388377702429695,1776156257506569406,-3933260407104067137>():
               if (this.N.contains(var4)) {
                  this.c(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"s20zxiv2goz6ky","TLY08ETvLzBPOOchC/uoQLKsv68q4/b81dhaCX1XQpfPXFAEweK0vHU3F4s=",7249930942511339259,8326893189332111301,-998076553994481873,7538606456451653494>()
                  );
               } else {
                  this.b(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"s1cct0e78a0i9p","m81XvZPj9Pbgr72FJsO2PvHRAFTWzQ2W9zxtL49SdNHJjFiTlp2gfVXJ",-5088348523013038112,1189400202444354734,8934353332492271837,3762686691414331202>()
                  );
               }
               break;
            case (String)com.yiyiaddon.m.b.a<"s2v3j6wh8scerm","XVop5RiUwqW3oGsBFumN48RGakrgQCd5/33oBCW1c5eQshU6Ff+dOyLFJZ3xJegK7/8=",-6189090611150811101,-3068418352509885452,-1536023518993773287,7904633817022759977>():
               if (this.N.contains(var4)) {
                  this.c(
                     var3,
                     (String)com.yiyiaddon.m.b.a<"s20zxiv2goz6ky","TLY08ETvLzBPOOchC/uoQLKsv68q4/b81dhaCX1XQpfPXFAEweK0vHU3F4s=",7249930942511339259,8326893189332111301,-998076553994481873,7538606456451653494>()
                  );
               }
               break;
            case (String)com.yiyiaddon.m.b.a<"sfoki23li5qdj","NF78AmDDs7tarlKrYZqPjuxEcd0RxbcIs72ER6A2pnlA35NbcQ++X8cGJVD0JGSLxYL1GQ==",2182842208882824990,7807045178824583165,4451030100589347170,3648585401359340810>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"srajcz6fguc8o","Sm1GQukdy89AOrOr7HYl0Tikuwz7/2k7YMmERM0SEPQEMIS9evn3H6g9",3831041404143892605,5010922424269360652,4688384373272180891,3996622816648488124>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s3k71h0yuyqbw6","hKDfPKqp6bcUDWpO/78r5yBAnYbpZadfw08a7aGy3a5ts6M8eLMuiE7KMOb3M+DeyJk=",-4753697687685911782,1512906068634311598,1885354186197609543,2853891331482953597>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"souzhkd0a9jhg","dp43chOUr9+w+9nCdvN3jZnN44idOpG/khYX0uztYqKFeuJHfj5RcWko",7710835593697135669,146165396655241154,-8429218477481812168,-7391167522798361925>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"sgmfzjhqsm0qq","ORwUWn8l3aQXIy8uLxeGZSWYmS20QhUZDXrXYkVPyGHEPySQdKlhZYfDF90aIHgCkww=",3046521569536238105,-1088491342053634588,6342277614565229729,-1860508269593780816>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"shio3cx3haj0t","mfYn2Alt70850x5kftYHcYi4mBzI8/Cfj+k4N5eOGnYdu1zhk7zD8ZB0",6684203256524745794,5607814594800712574,8726343319408549357,-5254554876478935269>()
               );
               break;
            case (String)com.yiyiaddon.m.b.a<"s2cyahh32yd7yo","efp9CmaegluD6MSp7ileGvjxt/zAifiU+xgpqMmCaFfDtPLdKkfVFUf72f0=",-5651160492906938885,-505296639184347643,-861527324869832531,5224686296408573257>():
               this.b(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s1c7vypcahxmm7","L5ShXBh+l2mGyC+/pFwXJN+9X22GUc6OjygWLi08Ri9CAVGP78Mgnw==",-6715993357443147083,3941307594823558713,1674353123759813212,-8105517307303604544>()
               );
         }
      }
   }

   private void b(Component var1, String var2) {
      this.a(var1, var2, true);
   }

   private void a(Component var1, String var2, boolean var3) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ne347muz9ss1","+re/5h+lujvZET/Ur3DO97cEjycK8WDshNEpz3ZJRiE=",-8227305138189391501,1941810436310581370,-8324494180944730450,5526131763731785469>()) {
            case 630833325:
               return;
            default:
               throw null;
         }
      } else {
         String var4 = var1.getString();
         int var5 = j(var4);
         if (var5 < 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s38007q8l0j140","+nAz/hfA4vc1lqZqxVh7Je5LXyv/hlq6iFgmdBlLccU=",8766776901329986143,2880121403923984160,-7981724161333239396,-7361648150085380031>()) {
               case 690574962:
                  return;
               default:
                  throw null;
            }
         } else {
            if (var3) {
               label42:
               switch ((int)com.yiyiaddon.m.b.a<"s10ilqdjqb2e61","n2WdMN/ilsvjtgzYyrqObcHuUjZLxIITd5mvq0WBV1w=",6395086866282898193,-352341327205937425,598548475121508640,-5951779371206491692>()) {
                  case 103528338:
                     this.I = System.currentTimeMillis();
                     switch ((int)com.yiyiaddon.m.b.a<"s1mkuonwkm5cg2","k0vvX0vJVziwWgV8oR66bFDAqebACrIzoX1nQEZt/q8=",7218774600790845565,4374904488636770592,6947948686001565999,3165515700361749500>()) {
                        case 2025649331:
                           break label42;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.c = com.yiyiaddon.e.n.n.d.b();
            this.a(var2, var4, var5);
            int var6 = a(var4, var5);
            List var7 = a(var1, var5, var6);
            Set var8 = this.a(var1, var5, var6);
            if (var8.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"sy784m9fiplnk","XZw6qmfkwltcN1aa//Qle0QZGGsXHnhkY7pVXPPd4r0=",960082731784052352,-3977535056574845101,-5037769950079956052,-5264445833747025541>()) {
                  case -288526787:
                     this.tX = "" + var5 + var6 + var4.length() + a(var4, 0, var4.length());
                     var8 = this.a(var1, 0, var4.length());
                     if (var7.isEmpty()) {
                        label33:
                        switch ((int)com.yiyiaddon.m.b.a<"s1o54jj8v2iiqm","Zhg/RsN31XMSvbJa7Q6PRTV/CZk36BIpykAS0VMI7PM=",-4625021307099334714,-5284703794202391431,-2103938080831717669,-6248453846846800118>()) {
                           case 266051568:
                              var7 = a(var1, 0, var4.length());
                              switch ((int)com.yiyiaddon.m.b.a<"s1yn58dcz61ntw","R5dNNte9UZjjbM8vldixYbopd1ZpUhRJNLTCxblja44=",-524768516747836296,3183704576000575645,-4272009088898206885,5194368183172756575>()) {
                                 case -2097576752:
                                    break label33;
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
            } else {
               this.tX = a(var4, var5, var6) + "";
               switch ((int)com.yiyiaddon.m.b.a<"s38epyenbzo1q4","4NapJ13x2Eosvdqss8BmIQN9kRmJGP4r1B2Fh994L8Q=",-6721104193795421736,7485076558958591964,8956184926400992722,9187561964559832142>()) {
                  case 1154519588:
                     break;
                  default:
                     throw null;
               }
            }

            var8 = this.a(var1, var4, var5, var6, var7, var8);
            this.b = var1;
            this.bR = var7;
            this.a(var1, var8, var2);
         }
      }
   }

   private Set<com.yiyiaddon.e.n.n.f.f> a(
      Component var1, String var2, int var3, int var4, List<com.yiyiaddon.e.n.n.f.a> var5, Set<com.yiyiaddon.e.n.n.f.f> var6
   ) {
      if (!var5.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1phtwr564g4q8","WK0/HG8WCFoRZy/nNYm+GDuWIIKszWU5oo1R4oNGCtw=",-1956056324921643064,3404494399997493231,7603015108609983900,-9181550585289834838>()) {
            case 1539878553:
               if (!var6.isEmpty()) {
                  int var7 = Math.max(0, Math.min(var3, var2.length()));
                  int var8 = Math.max(var7, Math.min(var4, var2.length()));
                  String var9 = var2.substring(var7, var8);
                  LinkedHashSet var10 = new LinkedHashSet();
                  a(var9, var10);
                  a(var1, var10);
                  if (!a(var10).isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3fsx24413soew","pXCWGUfoV5lnAZff2GVPBvjheDSOljkMLeMvX7cgHPY=",-8084287570694987427,5594079291135982710,1580759363039494591,7881233005006765513>()) {
                        case -1196221164:
                           return var6;
                        default:
                           throw null;
                     }
                  } else if (a(var6).size() <= 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2o27pt86ukjc1","WexLrwoE/ZSiycKLKjhE1mCBc2APv59oNk+ReeCx9AA=",2611398005598605774,-8897941324099652037,-3801892776240383892,-9156378119107223661>()) {
                        case -1581461266:
                           return var6;
                        default:
                           throw null;
                     }
                  } else {
                     LinkedHashSet var11 = new LinkedHashSet(var10);
                     a(((com.yiyiaddon.e.n.n.f.a)var5.get(0)).cy(), ((com.yiyiaddon.e.n.n.f.a)var5.get(0)).em(), this.c, var11);
                     int var12 = 1;
                     switch ((int)com.yiyiaddon.m.b.a<"s1rvg0u1hp6uww","RTKt/9OA32t3qySOSlyiRQbD5LsniGOPvQXRsqon+b4=",8654535815867605258,5862080237524182076,2232806594855262716,-8747748692258306194>()) {
                        case -16315982:
                           while (var12 < var5.size()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3w0azn6zuattd","BuE8QjBdF6VytT6LLlXdAUtCVRr0uXE9vD+t7/UV1ZE=",5961255209455424488,-2501768772966542410,5040907511334685605,3049175029595970013>()) {
                                 case 1835712903:
                                    var11.add(a(((com.yiyiaddon.e.n.n.f.a)var5.get(var12)).em(), ((com.yiyiaddon.e.n.n.f.a)var5.get(var12)).cy()));
                                    var12++;
                                    switch ((int)com.yiyiaddon.m.b.a<"sxi890fywi0gz","EVrwewpjCc1ILJ17t8qOxbl1T7vIHQKkO+z8chyo07g=",-6638520677956381193,2790580501378419280,5580420747218360156,-5335773116833625768>()) {
                                       case -1752340291:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return var11;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ey762l6tm2ae","g1dBkUxZJwh9V52EAJWTZlxRCL5pLCaaYtO7M52LU6U=",6674841918789342702,1857620345248631223,-5561980406622882706,-8280722297148127402>()) {
                     case -1867985641:
                        return var6;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var6;
      }
   }

   private void c(Component var1, String var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"su4n816lcc287","NAuRL4t/Vp/k8q/A9wnMgLbvqlrENfBDis4tu3iMClo=",-4850328134513977950,-2059727451162871848,-3327953468617309948,6252018963950468813>()) {
            case 1672424739:
               return;
            default:
               throw null;
         }
      } else {
         this.I = System.currentTimeMillis();
         this.c = com.yiyiaddon.e.n.n.d.b();
         String var3 = var1.getString();
         this.a(var2, var3, 0);
         int var4 = a(var3, 0);
         Set var5 = this.a(var1, 0, var4);
         this.tX = a(var3, 0, var4) + "";
         this.b = var1;
         this.bR = a(var1, 0, var4);
         this.a(var1, var5, var2);
      }
   }

   private static List<com.yiyiaddon.e.n.n.f.a> a(Component var0, int var1, int var2) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2w58p0zjtp2bl","wJ/WgT8gR5t+ti8lI0wNC92iTNTe+INx/tEZNugNHH8=",1820297720356904228,-3809343005355404900,7208021780882645548,-3193604437141015131>()) {
            case -163439079:
               return List.of();
            default:
               throw null;
         }
      } else {
         String var3 = var0.getString();
         int var4 = Math.max(0, Math.min(var1, var3.length()));
         int var5 = Math.max(var4, Math.min(var2, var3.length()));
         LinkedHashSet var6 = new LinkedHashSet();
         int var7 = 0;
         Iterator var8 = var0.toFlatList().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s17igskf5rnwov","xqp+uhrMeoKkTJt8OHhOo+xRjE0moQ3yazdDqv+YURk=",5228890862770134668,-2735290132251935688,-6106046015618352217,7645816086893962435>()) {
            case -1186748571:
               while (var8.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3k95ts3wowta9","cEygB98BiOWw9HFkZJtWyIgN6VF8i93M6GAI2bFtxP4=",-7281356464084381251,-8086348280193783104,3271715956844219677,-1733060455204221654>()) {
                     case -1963464649:
                        Component var9 = (Component)var8.next();
                        String var10 = var9.getString();
                        int var11 = Math.max(0, var4 - var7);
                        int var12 = var5 - var7;
                        if (var12 > var11) {
                           switch ((int)com.yiyiaddon.m.b.a<"s35jhgonh7xz5b","TnB0Kela7a19FS/754+9ei3BZlaSDZ3p+Afggk/erxY=",8571671408449825579,-484706508081358129,2224816782350746472,-1841116729728488494>()) {
                              case 264746323:
                                 if (var11 < var10.length()) {
                                    label33:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1wepycfw2cyt8","eEaXWz7G8xmyu8oMjzPCHnJ5trhIgJFla15zPHKlVas=",-4021604778587375111,2361528197023058989,-211464473711550392,-1212514047450737108>()) {
                                       case 2042288151:
                                          String var13 = var10.substring(var11, Math.min(var12, var10.length()));
                                          String var14 = a(var9.getStyle().getFont());
                                          var13.codePoints()
                                             .forEach(
                                                var2x -> {
                                                   if (p(var2x)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1bd543ui68rib","LXZOJa4UM1SBOrg06uNBW486qYBemFKyoMMT5Tn1zuA=",2648999519385915092,-9048987462982616014,4752684638499125060,2707422563686144297>()) {
                                                         case -1345457116:
                                                            var6.add(new com.yiyiaddon.e.n.n.f.a(var14, var2x));
                                                            switch ((int)com.yiyiaddon.m.b.a<"s195eqo6nqppae","sBRrewEBEF6uA3r13NXr9lbI9cwIPy36gifAjfTivBg=",-7999023788161644046,1204039835987903562,-5269889869891249878,3113235388611395235>()) {
                                                               case -1736396949:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s1jl1s153x7dyf","TfOrHZH7xUUy4P8rFe5P4aDJtNjE2EV9hgX9hYauB2Y=",-1705889257226443645,-3008895525754593379,-3790187004580509634,-9116139050324342170>()) {
                                             case 698537320:
                                                break label33;
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

                        var7 += var10.length();
                        switch ((int)com.yiyiaddon.m.b.a<"sr5atauji68lr","BwprvWjZJcgcRR7R892gISZNWfdUasoQq+Ob8vGAIXw=",6893119849812622289,3471563865779621481,-3193022577774187718,-2342681105277019064>()) {
                           case -1605960249:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return List.copyOf(var6);
            default:
               throw null;
         }
      }
   }

   private static String a(String var0, int var1, int var2) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"swuenpqmlre2n","bthZhowEfLeWyYJ0p0o4ZQK6P2B3l6VN3WUz3lpDg3M=",4186607299292450543,1003462736394019985,-6050861975000928248,5767209479625369826>()) {
            case 1217918344:
               if (!var0.isEmpty()) {
                  int var3 = Math.min(Math.max(var2, var1), var0.length());
                  StringBuilder var4 = new StringBuilder();
                  int var5 = 0;
                  int var6 = Math.max(0, var1);
                  switch ((int)com.yiyiaddon.m.b.a<"s1in0nowyvjtv1","rEVyyj6bEVBjMprMB5S4LtIGypwNNt2plgW2PdR0YcU=",8337593677433644398,5543319617113504787,-800297781870037954,-671215014463855342>()) {
                     case 754070352:
                        label64:
                        while (var6 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s33wnnr6a0yk71","8LhfKWZ51lARel9BZKmG7VFNYGGm3O4yoqR4Wz3Wniw=",-8580049690733312621,-6781805464415606951,5268872478648404283,-5848073969457262589>()) {
                              case 327307212:
                                 if (var5 >= 8) {
                                    break label64;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3p0uv9rl3hqxu","zjRFla9F+xEMOa0wP8yKYPFiTh9W+1u08aobIWZmRLw=",-1301381137337003046,-7523469776208834999,6557719547358544189,2246227706919454148>()) {
                                    case -972192588:
                                       int var7 = var0.codePointAt(var6);
                                       var6 += Character.charCount(var7);
                                       if (var5 > 0) {
                                          label48:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3fn7mfbq7rc9o","ZX0zFFCQtlxc34HTaO5XNeZCelaRzZfdZR75f5XSaFI=",-6035591789624942727,-3802413079064836229,-7602001891052997958,-3110738258818459253>()) {
                                             case -122287980:
                                                var4.append(' ');
                                                switch ((int)com.yiyiaddon.m.b.a<"sm7nb1ao1degx","IAZDjY5+F9Q9Km0X2qJUfVxMcBN84Ad2wlZArx5qQqI=",-3693618663383916323,-1477573024805938940,-4322316041721426907,2818052941126123501>()) {
                                                   case -2096182783:
                                                      break label48;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var4.append(
                                          String.format(
                                             (String)com.yiyiaddon.m.b.a<"s2lgh4qygp6s0k","aIlRDU4a7RqkXSz3JZqodGK6h5w+e/85KGio3jRE2pyMK+R7ta5JBQ==",-3072654238072793998,7455462990590081022,5826850704267990798,3996164589466863843>(),
                                             var7
                                          )
                                       );
                                       var5++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s2xuqffff75jel","pj9LaFUhWnZivBVWEYGNGvBY/PcAkwEKNDNwFzwagDk=",1350316947672682453,2052807703401354305,-4436399454007556092,-3388586680331238277>()) {
                                          case 1900700959:
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

                        if (var4.length() == 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1p7ugtledxmgh","s3N3hxVRU09m60+LbQ2XpHxatqUovrVfsAnGV3AT1Vo=",2307986465984782743,-2390726648952436660,-801389040384314230,4960173207238542535>()) {
                              case 1909531085:
                                 String var10000 = (String)com.yiyiaddon.m.b.a<"s9av9auqiw4ml","vyNuJNqpE5yv1DYj3dRobUpHxXO7J3yGSG9zTFd+",-3527560985168121302,3807733047363939277,-4805229089496562446,-607488992130112617>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1pp9lotkhykm8","E14bKFhQXP0NGZtGwCdMDTnFKZvxbUky6PFb7Gp4Hb8=",-2147896208003763271,8749879518771181644,-7756333889262704921,4690042160302316005>()) {
                                    case -1900433161:
                                       return var10000;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           String var8 = var4.toString();
                           switch ((int)com.yiyiaddon.m.b.a<"s3950mhjfk59gb","YYCwMDPOGcXNVApybUEUKlQ1aAcJvqUZkwtjFS8EqDs=",6952622805736963026,-5253845560130924365,8582230025425143573,1672518621777769880>()) {
                              case -951090276:
                                 return var8;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s14bfpneywl7n1","echAoBbhJmawrPenth5nvW4oJgwtpzCphIq3R7i/aIA=",-3612281260329907121,261977686859474195,-87284620745107968,7658666013286800202>()) {
                     case -1702345626:
                        return (String)com.yiyiaddon.m.b.a<"s9av9auqiw4ml","vyNuJNqpE5yv1DYj3dRobUpHxXO7J3yGSG9zTFd+",-3527560985168121302,3807733047363939277,-4805229089496562446,-607488992130112617>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s9av9auqiw4ml","vyNuJNqpE5yv1DYj3dRobUpHxXO7J3yGSG9zTFd+",-3527560985168121302,3807733047363939277,-4805229089496562446,-607488992130112617>();
      }
   }

   private void a(String var1, String var2, int var3) {
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29bz5a8x4s1k1","8Xj4gmET6q6q5UX2H0/gMfnVSZFR62sp3d+dzAHe39c=",9203288866322498585,-8907292326944906654,6695053261119795875,-1161236790614320610>()) {
            case 325961030:
               if (!var2.isBlank()) {
                  com.yiyiaddon.e.n.n.f.b var4 = a(var2);
                  int var10000;
                  if (var4 != null) {
                     label50:
                     switch ((int)com.yiyiaddon.m.b.a<"s2tk72xo5bf82j","nu6+fwoF8yAbOuh4+Gt1NlF9Ql24UtBAzx985M+/mKY=",-3548079402422539957,-6581424418431326484,7052002779636246264,8889757564805164784>()) {
                        case -273641065:
                           var10000 = var4.cz();
                           switch ((int)com.yiyiaddon.m.b.a<"s2d6nmavuxe81q","qeIa5fwvy5WMbH71PD5M1Jim9WKirQQo99cF619NP8c=",1831539088539065567,-835229582520198960,2954404432433599397,6797289623248281083>()) {
                              case 1007785779:
                                 break label50;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = Math.max(0, var3 - 12);
                     switch ((int)com.yiyiaddon.m.b.a<"sbj0ft97ftoxt","GT13yMsWj9FYrB1nZ62RQUZzHyvhloHHJBbqTgg5fAk=",-211225102567778399,6313673378353136811,6624583457817374302,-7178486695740018554>()) {
                        case 2130513608:
                           break;
                        default:
                           throw null;
                     }
                  }

                  int var5 = var10000;
                  int var6 = Math.min(var2.length(), Math.max(var3, var5) + 24);
                  String var7 = var2.substring(var5, var6).strip();
                  this.tV = var1;
                  String var10001;
                  if (var5 > 0) {
                     label43:
                     switch ((int)com.yiyiaddon.m.b.a<"sju6jdninb623","lGbmMhIScxEDKjPlgf54DuhcpMU/Tib0NYExpzSdYCI=",1757807620837447228,-8944718147137496034,2088615202879227094,1906564903912100256>()) {
                        case 300523505:
                           var10001 = (String)com.yiyiaddon.m.b.a<"s1v1hyd1hctrub","1CvzfBU+6d2NpiI535FeViO/pXMvNPSccBu/xxs5",2287848095295785780,8245479449213996096,-3669816604559222558,7277059752608482971>();
                           switch ((int)com.yiyiaddon.m.b.a<"sglx30i73lkog","OOMaRCerOHTKJTFqBDCqzHldmqhjS8S9f0APVQSm0Ho=",4897108605031069076,8341909342190964846,3969840174708362618,2602803108677146886>()) {
                              case -1888718081:
                                 break label43;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = (String)com.yiyiaddon.m.b.a<"s3pizpky6mg9be","wNYGktyRVWdi4dDemYbk4O3hrW0WjPUc4o/Piw==",-510486590335624637,4664701912018333127,6599201736554935114,-9205879572517995879>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2ucz7lh5yrxne","scZio/BcMbhITcqoyNLFHZLQyGmrGuDRm/YxkDVhYgg=",4549469929845450474,8903377260435087922,4044416646793895613,-8419374381648839800>()) {
                        case 799249590:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var10003;
                  if (var6 < var2.length()) {
                     label36:
                     switch ((int)com.yiyiaddon.m.b.a<"s3gi4wqwx2geum","y7tfk2aU3aWhh85OimS3CjMEs9ldrA+PcBGbGOnxuVs=",2136758668503447548,4691090359699233821,7054286055473277457,110578208362650341>()) {
                        case -774283249:
                           var10003 = (String)com.yiyiaddon.m.b.a<"s1v1hyd1hctrub","1CvzfBU+6d2NpiI535FeViO/pXMvNPSccBu/xxs5",2287848095295785780,8245479449213996096,-3669816604559222558,7277059752608482971>();
                           switch ((int)com.yiyiaddon.m.b.a<"stfriick7prqr","40z4Tm+jpdcRnPhms/+GQ5NSxIe6+M4dIJEBSB1v/T4=",-756469574415786233,9058745226060122865,-8987289689306382254,6791020846052651499>()) {
                              case 189539017:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10003 = (String)com.yiyiaddon.m.b.a<"s3pizpky6mg9be","wNYGktyRVWdi4dDemYbk4O3hrW0WjPUc4o/Piw==",-510486590335624637,4664701912018333127,6599201736554935114,-9205879572517995879>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2ykx15jwsz4yh","N6WXSHj9+PB5XXO+Xzq0snO0dIhcGymrCal4Xpnv/gY=",9093596189120317779,-1544040181510288576,8637963605121088843,-6130170535171375384>()) {
                        case 1831458634:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.tW = var10001 + var7 + var10003;
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sqypfg8ybivhi","uy3l8EPKH3kasdOp4sHstiy7YkMjwECpTAE8YoT88eY=",-5297708524438357239,-4180318268586482989,4622594080495689491,-5830272166981184226>()) {
                     case -1946104282:
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

   private void a(Component var1, Set<com.yiyiaddon.e.n.n.f.f> var2, String var3) {
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lkf1rydh83os","Lz/lQGulSTAkZcnHlhoYf7sprK3aOE/PO7R24H6aD9w=",8503649103751864799,4923353826109464584,6755352895340957293,9184475325402591959>()) {
            case -1265410574:
               return;
            default:
               throw null;
         }
      } else if (a(var2) > 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e3n0jikjohzk","UZWWfVpuQjp0bhsKYUfp/6GpCqAypdHaV8H7gpIRtXs=",3388594536310635097,4404579359105502600,-6307686824846416816,2808111061045647865>()) {
            case 606382987:
               return;
            default:
               throw null;
         }
      } else {
         String var4 = com.yiyiaddon.e.n.a.bT();
         String var5 = com.yiyiaddon.k.e.c.dn();
         if (var4 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3cm9y5brxdtyo","rJboUwq/1f+DYmZqhZ5gt913xF2x+CmAGS2LAHnkd6Q=",6967257559629286876,-5809826124248631297,4126703258091104609,2231042688071712847>()) {
               case -1212975150:
                  return;
               default:
                  throw null;
            }
         } else {
            if (Objects.equals(this.a.bT(), var4)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ckk0jiykjciq","/D0s4on5xFVsD7lvxWQai4NSUn2eLWtRE0fvwtz5pBw=",-7448286001130835222,-1647034189482136807,-4306495830678056339,-7131726016390515307>()) {
                  case -704327562:
                     if (Objects.equals(this.a.dn(), var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s32rljeondo7f0","MG/io+eQgr2bYYdBp4k9pwvwm9MAngOKGWhmf7tG2kQ=",3439719681118022871,5767723155367290310,-3309480556263174073,-6548332340149080177>()) {
                           case -253258257:
                              if (this.a.u().equals(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s27v77ys9rlh6o","nQ82rrdWK6TDrJAKAgqUyoYInvW0gHEGb3PYv1ORpu0=",-3220137772503035819,7754494581579135975,-6579157530368592224,7408244727147961051>()) {
                                    case 1746725436:
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

            this.a = new com.yiyiaddon.e.n.n.f.e(var4, var5, var2, var3, var1.getString(), this.a.o() + 1L);
         }
      }
   }

   private Set<com.yiyiaddon.e.n.n.f.f> a(Component var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3j14cstlz0xpm","NR4U1yzqject2c+eTtgDh5DALMpyLlIYklNGlQuB/Nk=",6062077050513365205,7875052894923014291,8431579337097155708,2025371384625123089>()) {
            case -1374870914:
               return Set.of();
            default:
               throw null;
         }
      } else {
         String var2 = var1.getString();
         int var3 = j(var2);
         if (var3 < 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1q8wvgrkqwxdg","PTFU7IAMUN994RQqst71r4ctmOy7BYqI0XjbF2aALrY=",3905522771207690637,6598513699753202556,-3969326721138599453,-582214550352684557>()) {
               case 536400437:
                  return Set.of();
               default:
                  throw null;
            }
         } else {
            return this.a(var1, var3, a(var2, var3));
         }
      }
   }

   private boolean a(Component var1) {
      if (!this.a(var1).isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qc7j95sa4vij","xYWylcCXEgKeEBwgBLlyLGbWT35XgsM0wIEdlc6M9g0=",3519885783818812026,-8835514044924460755,8174160482855602171,6515867811444379525>()) {
            case -1089333286:
               switch ((int)com.yiyiaddon.m.b.a<"sl30q88ge7pli","0XMkfKwfuw1EOVw1fph29nTNaT2k9DekzOPBP1FCupQ=",-105012076079735616,8154401540789624029,-6697636226838162510,-7473410315483481724>()) {
                  case -672162996:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3nj26tvxmswcb","8HPLZO4ryI/e6Bn/vv/yzluu99M9QiiZPkxqIgzfFfw=",-8948850761444025826,-3742250197244468653,2993415805246784887,-481728131128206767>()) {
            case -1102232261:
               return false;
            default:
               throw null;
         }
      }
   }

   private Set<com.yiyiaddon.e.n.n.f.f> a(Component var1, int var2, int var3) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ibiw230vf1pq","1m7kVldABm909soyAW6UxwY6vD5MZKwO6SmBwUchlik=",6417422666313485654,3563087106367652243,-6911185687201065141,4052785474206262262>()) {
            case -893350814:
               return Set.of();
            default:
               throw null;
         }
      } else {
         String var4 = var1.getString();
         int var5 = Math.max(0, Math.min(var2, var4.length()));
         int var6 = Math.max(var5, Math.min(var3, var4.length()));
         LinkedHashSet var7 = new LinkedHashSet();
         a(var4.substring(var5, var6), var7);
         a(var1, var7);
         com.yiyiaddon.e.n.n.d var8 = this.c;
         int var9 = 0;
         Iterator var10 = var1.toFlatList().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3ig7si0jlynuj","Pxxf1aNosNyz6NKLvg8JH+CeiOsWte+BSjJp34ttJns=",6523638992694305105,-3763903924725440715,-6860204973945356617,-4295082553318697313>()) {
            case -1522056479:
               while (var10.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"scabd4nqaqxq","UXXZ9NLJZNDzhhTi3TliRMDC01YBeRJeTJKoEnIU4YE=",-2999928466116163630,5034087396185860035,-3387506911417505033,-6424724865042934755>()) {
                     case -2100549857:
                        Component var11 = (Component)var10.next();
                        String var12 = var11.getString();
                        int var13 = Math.max(0, var5 - var9);
                        int var14 = var6 - var9;
                        if (var14 > var13) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1w3jitkoqman1","xwgfCnNzrDl1pVuvPdmuPvYQPduBlmOM8sHaRuAiKaE=",-5854946623598240179,-8686211092170385995,7390042395267593464,1395997913021803873>()) {
                              case 1929116312:
                                 if (var13 < var12.length()) {
                                    label33:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2kai4ltkziyrn","KSWio/iusJeYetE0U9WPDtGUCoLhaFZQ+Rs/W0nFop0=",-4985033864336794885,-2755678493128650813,5098211181873349641,-1278066462155835548>()) {
                                       case 396104897:
                                          String var15 = var12.substring(var13, Math.min(var14, var12.length()));
                                          String var16 = a(var11.getStyle().getFont());
                                          var15.codePoints().forEach(var3x -> a(var3x, var16, var8, var7));
                                          switch ((int)com.yiyiaddon.m.b.a<"sbwznvnxc7kkl","gs8TwDVzjVMCnnQBWFJgVQImMReie6g/S2X/F6DeHzc=",2485381392025638194,-3947916615199075022,-7638489007906795400,1066282101101791399>()) {
                                             case -197988918:
                                                break label33;
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

                        var9 += var12.length();
                        switch ((int)com.yiyiaddon.m.b.a<"s3qfrysx7g0xhu","qu2mS17AqVz3nY0NO0pJHKLJNfzH9JftC0tN1gSpA98=",8931230678391640444,6732896822018800977,-8298735555179442043,-3058339864440316098>()) {
                           case 1359085191:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var7;
            default:
               throw null;
         }
      }
   }

   private static void a(int var0, String var1, com.yiyiaddon.e.n.n.d var2, Set<com.yiyiaddon.e.n.n.f.f> var3) {
      com.yiyiaddon.e.n.n.f.d var4 = var2.a(var1, var0);
      if (var4 != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
         switch ((int)com.yiyiaddon.m.b.a<"s6c1c7flmp5o0","XOVSIp57+DDraK5v5OShCCMFaefhSKxXTD4vQCAW0mM=",7907999738638662063,-8214759441905143564,-7470853454578257229,-3416287096180215779>()) {
            case 1821245392:
               var3.add(a(var4));
               var3.add(a(var1, var0));
               var3.add(a(var0));
               return;
            default:
               throw null;
         }
      } else if (!p(var0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jpubo32a769s","9ubmjmU+mL2Zlf3blao/lc9OOBIDAjf3lIvvq8n/3TY=",5893350521995022743,-1751532961612312455,-5036073201288684590,-608438633849796576>()) {
            case -891879194:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.n.f.d var5 = d(new String(Character.toChars(var0)));
         if (var5 != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
            switch ((int)com.yiyiaddon.m.b.a<"s39wz6cj4ax1fb","RF/FtbOEwU3PPz0tJU4+9ihticg7z9YX6I2qB1+riKA=",-4826501564006057971,-8468591736074642696,6149112508112986793,-4430041280354782213>()) {
               case 2019446138:
                  var3.add(a(var5));
                  return;
               default:
                  throw null;
            }
         } else {
            var3.add(a(var1, var0));
            var3.add(a(var0));
         }
      }
   }

   private static com.yiyiaddon.e.n.n.f.f a(String var0, int var1) {
      String var2 = Integer.toHexString(var1).toUpperCase(Locale.ROOT);
      return new com.yiyiaddon.e.n.n.f.f(var0 + var2, com.yiyiaddon.e.n.n.f.d.UNKNOWN, var2 + "");
   }

   private static com.yiyiaddon.e.n.n.f.f a(int var0) {
      String var1 = Integer.toHexString(var0).toUpperCase(Locale.ROOT);
      return new com.yiyiaddon.e.n.n.f.f(var1 + "", com.yiyiaddon.e.n.n.f.d.UNKNOWN, var1 + "");
   }

   private static int a(Set<com.yiyiaddon.e.n.n.f.f> var0) {
      LinkedHashSet var1 = new LinkedHashSet();
      Iterator var2 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s38kf9v14t3mz4","rAeklJdVEUN32AXrLmo2Kj4BWMRzE50Pbxritn3RGoM=",-4925047538419669988,-5216322575435444716,8671949898693062160,-4035586098552898507>()) {
         case 272247910:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s27r4fv0lq8cfc","cINLUnYVTsze0eemR1m7BxnzdVEUPRjOimPhRi2LTbY=",-5083254044511421381,-8873976603365794062,736162156633767034,-7216473585341242313>()) {
                  case 796215542:
                     com.yiyiaddon.e.n.n.f.f var3 = (com.yiyiaddon.e.n.n.f.f)var2.next();
                     if (var3.a() != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s3mwbv3xud8fl0","BbdM+NTSNFb18nhP0amgb548tn2KArVAL9Cz85SKySI=",3146795030925390481,-3687651401836914095,-2250542278774481243,1373647985413122516>()) {
                           case 217849919:
                              var1.add(var3.a().name());
                              switch ((int)com.yiyiaddon.m.b.a<"s3hgyddebztobm","5vGKWWW0fBgjE4cjsCLSm0AfeFLLEjUHKk9zQ0YXVxI=",-4462315705855168856,-1709144882148316964,2485812346195290834,-3883188481301263166>()) {
                                 case -1191895882:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3u5syntkmm1fw","qWGl4EcvoCK/hj0Sb2NMACfg+bz+YrQoQfl3qGurhOs=",4999845741880330500,-7979838643687626079,7928051549585087541,-219687559519166561>()) {
                        case -1436039958:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1.size();
         default:
            throw null;
      }
   }

   private static int a(String var0, int var1) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jsnxupon4my9","O1m9ekPjYem+XLCDSOTGP8zYTXaq/9BKdkcIY/OyL4M=",4941208380394588823,6737370958734557741,-6838395905981857058,-4670790498966906998>()) {
            case -1773211420:
               return Math.max(0, var1);
            default:
               throw null;
         }
      } else {
         int var2 = Math.max(0, Math.min(var1, var0.length()));
         switch ((int)com.yiyiaddon.m.b.a<"su0f8e9tswfsw","4+ZcgMT9LUKjZ8iuEqFEuYdUEc7GL7NqMKmTcSj1zgg=",8616237892281607676,6494141568446443606,-378846454912959938,-2500431486384766272>()) {
            case -1891322619:
               while (var2 < var0.length()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3pxrtn291em9x","rEs5tksz147Bzjc+KB21pF/CsN+qLsn9T7oUbe+p4JA=",1108858316142555848,-8339553757343997571,9136195884842937203,1285562481427715530>()) {
                     case -1944536890:
                        int var3 = var0.codePointAt(var2);
                        if (var3 == 10) {
                           return var2;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3mpe3fkoqsgey","pCj7kcWlzzYv2eRM2aCrNsdPQoAp2/7Erg53RyKvRxY=",-3527609179003592165,6070933928436759038,4714469621066666229,5868753072614919994>()) {
                           case 1219489335:
                              if (var3 == 13) {
                                 return var2;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s5mpeb9tiud9r","gAdXQoOEVR+NBgxNeZgus4ylgHXLUEgaLBJ+d4UO+Kg=",1114319225249850433,365781278932869556,8703039836919472244,-7293035912812665604>()) {
                                 case 732022222:
                                    if (var3 == 124) {
                                       return var2;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3t8rvdi3h9ehu","wpeH5sJrwhZ78lLNHBScSKR/vsmcg8K5qtgaLBgJS18=",-8847193813368941040,-3460205029071041202,6534240052660558363,8623418006079006742>()) {
                                       case -338371442:
                                          if (var3 == 65372) {
                                             return var2;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s25ucw5z8ld8cp","gZdHvxpTZfTQvuxjjcWklYlDc03bgMcXEIWzjzzKtng=",4994807740394783919,-43868878089273252,-4669958157422461688,229781084566967481>()) {
                                             case -267800764:
                                                if (var3 == 9474) {
                                                   return var2;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s3frjgy6shq1ys","9swYNa3NN9UxF0lQIyN1a4FHcPWYmj8BF28teyRAWqQ=",719474341598336822,1290225619585899980,-1913602554477896988,7091292434837358524>()) {
                                                   case -1982735168:
                                                      if (var3 == 9656) {
                                                         return var2;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s1wip6z3m7tdjx","e/9RljRPNzRv/Tykz+FCB9ELihbQZ+UIGc4S0KqLJcg=",1483236656337706094,8593666289630014308,3416047010858307272,-1138593406290617500>()) {
                                                         case 20251797:
                                                            if (var3 == 9654) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3ib59kj62jixa","kdOMz8+dOcuGNan6NjLPhBcgIGKNUuXT9HLK6sb8ANE=",9176730686721551913,-327355269097765254,-1354867779309149929,3608002607538794316>()) {
                                                                  case -1873087459:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2kcx18v8g8z2l","wE7ZpAuvLC9+MNh/N+j3zXyNhOeeYDY1D/KqXxznENw=",3044427629483325104,-1989968620576284831,-8781540474542499741,-551240369026802684>()) {
                                                                        case -708577696:
                                                                           return var2;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            var2 += Character.charCount(var3);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3b42of29uhlli","VmIpkYrshhkwEZzjhzb+7daVHsvZXWUlsyDUZEq74lE=",5561048291089724054,7709649545645370191,5975801940538755570,5277149725303065163>()) {
                                                               case -323445783:
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
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
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

               return var2;
            default:
               throw null;
         }
      }
   }

   private static boolean b(Component var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lfhpqkwe2mu2","x6qfpMYstxJnf7bSw9IZ794UN1eymAT2Mm4TyHiBD4g=",8663592555859161659,-2340230067079794831,-7256718140816171896,5015961747909501961>()) {
            case 1083457758:
               if (j(var0.getString()) >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4vph39sfl7or","dcL7nFdSZkas+wCRifXsm4K0wRQApLJ7JsxR8/t9KCE=",3699305578310956730,1636875639493341444,-3187627493675976100,6980858377001392166>()) {
                     case -1805802911:
                        switch ((int)com.yiyiaddon.m.b.a<"s1rabgg9q1wwph","r3D+Wmi1PqWKXKIYAFrlJd2A8BwOJ/yuNnSlj71Ie20=",2729138580458792362,-7776026011658854087,-4006144309475985349,-4292256760556911388>()) {
                           case -85268659:
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

      switch ((int)com.yiyiaddon.m.b.a<"s31q1qjwgzgbdv","e2X0nIIQ8jjpu4m2/S0XtH2WDgPXek9IJYcdTkC9juo=",9059285849712220053,8709330027329559862,9091837748079515116,7822529216822491836>()) {
         case -1061438361:
            return false;
         default:
            throw null;
      }
   }

   private static com.yiyiaddon.e.n.n.f.b a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ijmu21nsnlas","5szncl4JfN3sq+34Q2QB/960ff8hZWuI2YgLEfzriY0=",-9015135787009414488,9129089215859369364,2733152575763434508,-2687980334710565711>()) {
            case 1944137686:
               if (!var0.isBlank()) {
                  String var1 = var0.toLowerCase(Locale.ROOT);
                  int var2 = -1;
                  int var3 = 0;
                  String[] var4 = W;
                  int var5 = var4.length;
                  int var6 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3kznr1pqfjeqs","um5HPoZ3lTFm6FGNGVVIfpds/TA4yo2RO0P4EaY921M=",-9195966386007947103,2515351360506426772,6340064863385163252,-4406915716021956501>()) {
                     case -162688742:
                        while (var6 < var5) {
                           switch ((int)com.yiyiaddon.m.b.a<"skc1ceogey9bq","D+I+uAEIskS8PyUKzzyIcdF4jDkiorixTopFum4YAM8=",8853099058919634806,-3164691943116521981,5101631010547736361,9147921763303461678>()) {
                              case -1463560749:
                                 String var7 = var4[var6];
                                 int var8 = var1.indexOf(var7.toLowerCase(Locale.ROOT));
                                 if (var8 >= 0) {
                                    label72:
                                    switch ((int)com.yiyiaddon.m.b.a<"s20krcjvzczpyd","gUDoMZ+vOPwo52JFarVwQocIlIu+YI4/c7tbHWfo0k4=",-2422663417867014058,-1518279268821438647,8705340941122744811,7623029188112218426>()) {
                                       case -1905383833:
                                          if (var2 >= 0) {
                                             label70:
                                             switch ((int)com.yiyiaddon.m.b.a<"szbtvx0lp41ny","jcKsY0frSdIVljNz3D3rIKoSpUtyQp8KYF/i7nCLZO4=",-5773996028237789600,-784668886972550846,7938255409235116629,-8931654533224527918>()) {
                                                case 1832833937:
                                                   if (var8 >= var2) {
                                                      break label72;
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s2fj5uwjc9byo2","9tFLkmxHiafYvNlCY3dxWHS5D/MkvkMHx1lrKB4dKr4=",5953440620808776535,-4136774230680757215,-2328798160425002575,967699983280039504>()) {
                                                      case 1774901018:
                                                         break label70;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var2 = var8;
                                          var3 = var7.length();
                                          switch ((int)com.yiyiaddon.m.b.a<"s1xs0z784v50xa","54Ifjo7EYpycsCjVRefXMV6KlB2vAR9ap4vV6GUIPx4=",-1354065665865377787,-2171941425811983719,5576047235059032421,-499215704673281433>()) {
                                             case 1037141365:
                                                break label72;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var6++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2optksc20yhxo","JWxbsIf8Jk4UNlWmILBCAvJAGQXCuSzerCddAAxl/mM=",-4401396521367574847,-5922879232967114842,6308146914180976556,-2430113766754443936>()) {
                                    case -267332714:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var2 < 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s275hqwo1y29be","3NmdWfnAVtBxDOps7yreJA7JISwcfn6kNL+tjW6Urm0=",-6904252026078616433,-8792359458617771443,-2201747786544674672,-3421108899040477250>()) {
                              case -56811629:
                                 return null;
                              default:
                                 throw null;
                           }
                        } else {
                           int var9 = var2 + var3;
                           switch ((int)com.yiyiaddon.m.b.a<"s6vstrufw7w0h","LnaAInF61jXq0M8txb5g6KVHTajT5UTt8MojJkeDVgU=",-1140769496312387336,676065699661671917,-3792362793896057520,-533197561610099181>()) {
                              case -1707541611:
                                 while (var9 < var0.length()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2clo30hw8nonp","JffixkzIDi5ekPWMfMUfIJqW4cpndXgEXFC9GW9xoZM=",8457652167221406230,616854009983828434,-7391993700454597364,-8901031984688332945>()) {
                                       case -20916232:
                                          var5 = var0.codePointAt(var9);
                                          if (!Character.isWhitespace(var5)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2w5mh8u5ionbe","CpXxyU+f6YshTb5AZpu9YOJh3FHXKIap1p5X5WfZrW0=",-5204022040614181897,11688465641022039,7433170496831508619,2335567531776387051>()) {
                                                case 557466509:
                                                   if ((String)com.yiyiaddon.m.b.a<"s20g0694xc4fr8","1kIONQg298B7Aqh+w0e9lp9X2aKa8B4THACAGTd3Rq/lXoTYRRoIGxRFMMrOhSD/qXNWIC1HpLMiFgrr",4446880837673540539,1563269798129351702,4460136423478628754,5277300519550013679>()
                                                         .indexOf(var5)
                                                      < 0) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s29ws78q8hhn72","j6o+NjIxEfFSATBjW22+sdy2ZVZ2Y+6bBMPBQIwxPWg=",-583020447322094294,6871458958583035854,-8461074740292335416,2146911980673916590>()) {
                                                         case -457384360:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2rw451zr2mgom","xkEgpNLWtnpdZJiGx6z3I06K1TeDHqoknabpH+aCwaw=",-8000224947382579131,6347648618618270265,3455741581343122482,7580981935800610868>()) {
                                                               case 2053422937:
                                                                  return new com.yiyiaddon.e.n.n.f.b(var2, var9);
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

                                          var9 += Character.charCount(var5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s18x5j4xek5nbk","sp7Bu8h89QA0HWnxtxNIg8G2SN/IPHs1ely40C+qYlY=",389621215514081335,-351971877937429969,-8820479307025973056,2856390053823085832>()) {
                                             case 8427263:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return new com.yiyiaddon.e.n.n.f.b(var2, var9);
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sroywxu88prus","dlCPzx2GDZ1KUBtTkQZ4uavsmOX5BWkDef9ez0dGoYU=",-7905775908318383886,147806987398597237,757290995415230898,-4504610610158012492>()) {
                     case 964013911:
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

   private static int j(String var0) {
      com.yiyiaddon.e.n.n.f.b var1 = a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2iml3y7hvvwxg","DeCSXQany9hdHwTldAimggU3pJz08ODIgfeTGjUeJDU=",-8470677026007430909,4250336424649749728,1816900136474775779,132053361805556619>()) {
            case 1528164555:
               switch ((int)com.yiyiaddon.m.b.a<"sqmizsxi7kzv1","cv4yloeBb23i3xstVDA0F0GfJls3J+8CcuWGa64i1ww=",7559116454370830035,-5559272820259667267,-9107812763946692301,-685944370684249189>()) {
                  case -1397278614:
                     return -1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = var1.cA();
         switch ((int)com.yiyiaddon.m.b.a<"s2fk1f6dqgu4ry","+QzIge/g4aDn5JoymKmyN8pNwrJ/hTZPKVx5xUkYKzY=",-6359023088223256124,8119859484719999916,8738517118202788283,-6748545531902411810>()) {
            case -1104822791:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static void a(String var0, Set<com.yiyiaddon.e.n.n.f.f> var1) {
      String var10000;
      if (var0 == null) {
         label76:
         switch ((int)com.yiyiaddon.m.b.a<"s2en2it9436eie","STHgqCe2TjIU0NIUNtYuvy2X0+Jn9NbV4ObB9EoVLv4=",5058268261108083833,8879418329982071914,-7392449389792025934,-7271583282246936065>()) {
            case -102308780:
               var10000 = (String)com.yiyiaddon.m.b.a<"s3pizpky6mg9be","wNYGktyRVWdi4dDemYbk4O3hrW0WjPUc4o/Piw==",-510486590335624637,4664701912018333127,6599201736554935114,-9205879572517995879>();
               switch ((int)com.yiyiaddon.m.b.a<"sw97mdtvung6w","xmNcXLbxLyi0reVJoJb0PnQzWxbmwETtLXqEGqTdi5Y=",-1610843264598274393,-4925467090534042578,3901487046990361603,7925076744181541842>()) {
                  case 1982837106:
                     break label76;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s3crce9iahp7mw","Zo2CczTBDBhLv4NiPebC3kMrCbByUppCp/N3o1402hk=",5082231689763177787,-2392569393099085354,2046211497215038773,-2962724560670654230>()) {
            case 1081048908:
               break;
            default:
               throw null;
         }
      }

      String var2;
      label108: {
         var2 = var10000;
         if (!var2.contains(
            (String)com.yiyiaddon.m.b.a<"soi0byripoua","p/T5/d8IWJeTy29VqTDHh5Rq+9fdlKlEq0zzpbCa",4149795658186852681,-3615072248646404162,-854784781652431,-2281496085658343384>()
         )) {
            label72:
            switch ((int)com.yiyiaddon.m.b.a<"sqnjwfft8fviq","GgWbhhi7w80UbF8DvVOzIWTjl5j+0GieN3ratqBIrXw=",-2574045496316873904,-1622233899232674370,7247151074359405362,3378857417559653661>()) {
               case -1898464873:
                  if (!var2.contains(
                     (String)com.yiyiaddon.m.b.a<"szb01wy3v37v9","aMtLEI25TZTMMMbF3wMrZZKNWKHhKcCLvcOAHDNI2E7rjPYqAordYw==",-7139749263457274066,6042500079379149343,4369196334407939236,8503421527268831367>()
                  )) {
                     break label108;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s16t2vyxwznx0f","hcFAUM49dumctABFLyEj3VtI8GUjkqULqlOw4yFWXEg=",-5339738696117845626,5748041324868296293,4662743748007036259,-992632215185532089>()) {
                     case 914395941:
                        break label72;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var1.add(a(com.yiyiaddon.e.n.n.f.d.SPRING));
         switch ((int)com.yiyiaddon.m.b.a<"s6kx9qzsgtl5x","jI/zzrDfEyIlZBd7ziKl3Ld+pm/Z0I/m9IrPcJGxzZg=",-9118851159554361470,903553074118671274,6047775550847012037,-7310267631587371671>()) {
            case -191314904:
               break;
            default:
               throw null;
         }
      }

      label101: {
         if (!var2.contains(
            (String)com.yiyiaddon.m.b.a<"s8h6u12fcitxe","Lr19x1uwyfVPv+9T/wRAGvvh28MTGaF3sBM0tfvc",8464135820668136142,8934693976676741595,-6207867503639410453,760876119093823814>()
         )) {
            label66:
            switch ((int)com.yiyiaddon.m.b.a<"ss64tz741hc5w","rZq3YTUos+kiaLctU/Bu2Fox6mpF7fH02YTsfOXHT54=",3797920566873205187,5462242727683936564,8281565006672452427,-1792336913048962402>()) {
               case -1541155920:
                  if (!var2.contains(
                     (String)com.yiyiaddon.m.b.a<"s32skrh6u21z26","BcrbEcUFerW6snIU96YB0Jb8GkhZjZYU+6y9vtHOpgIS2ltV6dI5fA==",-3454912739270276985,8597645581553722231,-5999900655492457789,-7989704138337849472>()
                  )) {
                     break label101;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s65ut59c49uwh","AlfkvbEHOaIVBuJ1S2WIqCmqvo+afywqmQJ1fmBtAtI=",-268541502830417315,2395381218305050121,5746184576288820928,193594101017484943>()) {
                     case -674714915:
                        break label66;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var1.add(a(com.yiyiaddon.e.n.n.f.d.SUMMER));
         switch ((int)com.yiyiaddon.m.b.a<"sjwr8dnsc8nw6","rZOLXaxi+pxDKaPeYaTEcjJxl3+HC0cqpSEFYqwPKgo=",6952993433559873727,3646031755727474785,8691662578332762500,6250895385287380291>()) {
            case -1263230584:
               break;
            default:
               throw null;
         }
      }

      label94: {
         if (!var2.contains(
            (String)com.yiyiaddon.m.b.a<"s20nzt9199o5sn","0SUozeetXoM1UXejW/qEZybx/D+Fqae+pudJP0pb",-3036220150652579548,2993186794367486849,8329136257645088752,693591545118200291>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"szqvyrowzitys","8fRXjdgHFvRH8Chyg7TpZ5LoUSVgckwYFs0dcYQ4cIs=",-7097261453116727242,-2967905203064827122,-1143402843005861118,-8779616461920523806>()) {
               case -435211704:
                  if (!var2.contains(
                     (String)com.yiyiaddon.m.b.a<"s1mj53sw1ahjx3","l7Pjzr+xk+C3mRxp29CYmlwbH9Jl1DAYAb00zfq0D2gKm+RRb3uHgg==",3601442013630164342,604294152949879676,-4934423686641320085,-48534417478007027>()
                  )) {
                     label58:
                     switch ((int)com.yiyiaddon.m.b.a<"s4if1uvvcikqd","EHKgjmch/98L1Io2wOZmIkaJp+ZScQOFZ8GfPTn314k=",-1058272350755631606,-1956592849492254045,-9023820620507798958,-4626193228696081283>()) {
                        case 250448501:
                           if (!var2.contains(
                              (String)com.yiyiaddon.m.b.a<"s1cklp8duld3dt","/U3zdUuK5vzFVgEn5CksZ5qYz2CNL99gLeTRXA0lVSJc4j56",5733098347037245099,-4058945442029769277,-8400738798861051017,3223430485777714854>()
                           )) {
                              break label94;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3ozuzuyx3set2","OEunDwuLtV6zrxUUnXx0rikgbqrRJbsSDz107qITNt8=",-6826387203196817849,-67134876222934700,-801012193488616968,609186464294657077>()) {
                              case -1433922407:
                                 break label58;
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

         var1.add(a(com.yiyiaddon.e.n.n.f.d.AUTUMN));
         switch ((int)com.yiyiaddon.m.b.a<"s2q0gi3z6eotbq","VyFPlJboJAnBBDqIF+qeiHyns4aeEYDsp4FJX7AOPCY=",5652766755108938734,-6015956977326243712,-208321090967651219,9050727814834644252>()) {
            case -670912712:
               break;
            default:
               throw null;
         }
      }

      if (!var2.contains(
         (String)com.yiyiaddon.m.b.a<"s3oio3mzeo42ic","qiV/Lg4X/1SMe2a52vHcQxRkY0swRnaeFedz073Z",-6605765055036887769,-7931619410365589816,-8162777674133981560,2386313522252548061>()
      )) {
         label52:
         switch ((int)com.yiyiaddon.m.b.a<"sguvk5kh14gne","U/LTlg0Vcw/wUw9ISgBlfitJTs4mUdpM0L6VdQ+MA4o=",-7774325462190409790,-3521046691752175547,288130022708438916,-1123688864033831289>()) {
            case 2033818305:
               if (!var2.contains(
                  (String)com.yiyiaddon.m.b.a<"s3qza0b0m8ow5s","l5aZVShuIBcHQOLKT2Q9ZM5lbBTbl/BjqWqp1j0H84D/MZR9vD2Ecw==",7835198551297442761,97084833920097113,8101554512325739352,-8941695514974078267>()
               )) {
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3h88kjwwniy4n","3nrHUN1TRhC6VqXeGE+lMJGZXRbWA5pFAIheA91evHw=",-5019287828243938418,477695639248344148,5645725064531574249,6920008452666898494>()) {
                  case 151687183:
                     break label52;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.add(a(com.yiyiaddon.e.n.n.f.d.WINTER));
      switch ((int)com.yiyiaddon.m.b.a<"swr1ldwh0w3lc","E9GlSmpwTBsKkNI7fLQKyp5WXwjvhyDU7vNUiiJoZ5g=",-1758491110220718629,-7398242153789326642,-2105792340287439365,323739290487054947>()) {
         case -1998481782:
            return;
         default:
            throw null;
      }
   }

   private static void a(Component var0, Set<com.yiyiaddon.e.n.n.f.f> var1) {
      ComponentContents var3 = var0.getContents();
      if (var3 instanceof TranslatableContents) {
         switch ((int)com.yiyiaddon.m.b.a<"s97cooqii5554","GQNp4cYHbzijYmv8WozV/AOFnwbwE2SPuyQb+XXBJPM=",-6058524555521264531,5298313087132627996,1210932986752305518,1449348765525817993>()) {
            case 579188297:
               TranslatableContents var2 = (TranslatableContents)var3;
               com.yiyiaddon.e.n.n.f.d var5 = d(var2.getKey());
               if (var5 != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
                  label32:
                  switch ((int)com.yiyiaddon.m.b.a<"s22ubna3lytcer","7BxDVzTnXtcZ5lKqdJF7LqKDJQokDMyqx6j0bVqBZHs=",4370164118742669680,-237964649892123334,-7178031119343124185,-6215505041082388894>()) {
                     case -1303374335:
                        var1.add(a(var5));
                        switch ((int)com.yiyiaddon.m.b.a<"s3llkklbuuylhm","qPNJ/dRsHHTPa/GfHD1rDEswQKtw20xT+F/3oPZxiV4=",524368919223896075,6341110976118242868,-1892612610754138534,190737300758736033>()) {
                           case 1448928508:
                              break label32;
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

      Iterator var4 = var0.getSiblings().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"skk63k1z80z8q","GEOaVGG+4/lqSMKcAaS8GSrPlCu9XXAGsnJ9FQ/HuCQ=",-4936431896319016293,-1641939261608224503,-6451416690478654381,-1544631635021692241>()) {
         case -624717680:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2wy8ug7wm4n6w","sUhf3Lynx1iutgqSNw3VRykl0gecR2ajbIOXb+AJhHs=",-1165491917947349769,-4008836557471974884,7055375164955394307,-3694087922457266512>()) {
                  case -1793276307:
                     Component var6 = (Component)var4.next();
                     a(var6, var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s1iqxt2kvclz5h","6tvyZylcxwejuYsJpeTloNbif+TEG/qkkDBCOJR0odY=",-1225273338340121693,955050133366130044,-3575526067176466635,-691917598135820162>()) {
                        case 1214666728:
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

   private static boolean p(int var0) {
      com.yiyiaddon.e.n.n.f.d var1 = d(new String(Character.toChars(var0)));
      if (var1 != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
         switch ((int)com.yiyiaddon.m.b.a<"sw4o3yp37rguf","q7AovS4IPAd/+rT32Y5h/+lS3EPhmEVkq8Jai83j2hI=",-2465842329233098466,-8418731746481506045,555129926659587732,2113063221822401309>()) {
            case 1294463121:
               return true;
            default:
               throw null;
         }
      } else if (Character.isWhitespace(var0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ron0smgi9vwc","8TJfF1WhwJnOgdlL+ObpbRpiOgXeDiZfWxuQwG9C1kE=",2579841087396734986,1644026695330595232,7586739400805923827,-4689700604737488609>()) {
            case 1506688168:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = Character.getType(var0);
         if (var2 != 15) {
            switch ((int)com.yiyiaddon.m.b.a<"s3u4x2wrbg1hai","7ebO623uGZqElUrWlPFc9GkCmGVkBIEgd+3eUS6TTxc=",4040580617783581511,-9169716628910328549,-6225801779513038606,7171863048058165916>()) {
               case 1910147744:
                  if (var2 != 16) {
                     if ((String)com.yiyiaddon.m.b.a<"s2ptrtr52oys6q","rlqV7VEyjEjNXZowGyEJMGtwJ8aqG+qtmJYXljQVfL968WFtPqPBnahNSz0ts+Z58cksUoDo4cNpXaGfW67pK11nr/jkdqtKxkw=",9007703422126900633,-1304777462275970880,8612731336419113925,7704763320512895804>()
                           .indexOf(var0)
                        < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"sdw4zlb6sq5f7","A0ciaLXyJm4GwBnwvQHu24jmvuNScJdVyHi9akjXx9w=",5741494545910537308,-7897864034289819386,-7688387830731059049,-2041982746000158830>()) {
                           case -1909925516:
                              switch ((int)com.yiyiaddon.m.b.a<"stzok4kbw6kfm","33cd7XJBdEDGCOnYYTjeSg99/GhDYRZZJeHy3581RHQ=",8450916427197214769,8656289416783633562,-5943239094980493242,-31779312388242485>()) {
                                 case -679764584:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s24esvgfrcbh2z","Vhnd9lbpSnea34FNeGc7wPxDR5xy7dkgFz6pIx6d0SU=",212281468043581381,4711383397919801598,-5738485372446452100,-9195926784636761726>()) {
                           case -371688847:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3in1ytxpsty3g","oCYDpwaHMBgXQIZ1vPn6dzhz/KmR74iGcQTFI8a0TPk=",7936539052173048989,-1070016296841982133,-1044195019627116118,-711030638678956719>()) {
                        case 701020108:
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
   }

   private static String a(FontDescription var0) {
      if (var0 instanceof Resource) {
         switch ((int)com.yiyiaddon.m.b.a<"sjufaudy0kbw9","HOTgHHldWQMx6V+LTTynLU3xvoDeUZ7YsyPQUv5Q5+U=",-328529904769837207,567487970487513478,4852109029510785121,-2912994271122268875>()) {
            case -758569489:
               Resource var1 = (Resource)var0;
               return var1.id().toString();
            default:
               throw null;
         }
      } else if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sgctygw3x1d2y","Iv9Gq2g0+FiPoFgiUuSRNCe5QaOVraRX1S4g9gvB758=",8928427702788222150,4915172440592802788,7636262767491384216,-9193984558801561257>()) {
            case -1808627827:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s3raf851unyipk","tSEgMF3ZmiAWHkS4Ksup0EkS1CcIB/dsqAfbnmEFjZ/5unHysYk/tgaqwwvyi/fYBbbpJ2IKoYaedi7y9yM=",-183565590740283439,8676106263217758264,4398462184578140244,-131383787040586820>();
               switch ((int)com.yiyiaddon.m.b.a<"s2n6kzgv74fks3","EmiEHIvMyYkSjsPYE8HGsfCB+LdRPWTXeImdGqSJ01Y=",472507019183819612,8541454584142927588,4860860023458451910,-3965341887865483995>()) {
                  case 996634231:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = var0.toString();
         switch ((int)com.yiyiaddon.m.b.a<"s34wn4wxzcwwgh","3bK3BLG+4C0grTDL4+h+SmRFJspRO+f1v6KoaLDBSDE=",-416201688437388980,-6617042253534829017,5753322830341108432,-6140650396340750630>()) {
            case 767383089:
               return var2;
            default:
               throw null;
         }
      }
   }

   private static com.yiyiaddon.e.n.n.f.f a(com.yiyiaddon.e.n.n.f.d var0) {
      return new com.yiyiaddon.e.n.n.f.f(var0.name() + "", var0, var0.m());
   }

   private static com.yiyiaddon.e.n.n.f.d d(String var0) {
      String var10000;
      if (var0 == null) {
         label76:
         switch ((int)com.yiyiaddon.m.b.a<"s33vmen6thmf7b","FGeHkqsK+3/uPolcs/rBgI3KWC+0V6k5o57ISeKRve4=",-7247991699385706232,2377020341029139779,-1812709421612005565,995562555381270231>()) {
            case -1921632736:
               var10000 = (String)com.yiyiaddon.m.b.a<"s3pizpky6mg9be","wNYGktyRVWdi4dDemYbk4O3hrW0WjPUc4o/Piw==",-510486590335624637,4664701912018333127,6599201736554935114,-9205879572517995879>();
               switch ((int)com.yiyiaddon.m.b.a<"s1crfhsdu06eg","PcJTt7ZWYZhE4lAlRj6lr9dW5aWXlgZ4Hm/h6Upjyvw=",-1483434203752423395,-5033703081265661379,9077796320496969945,-8224716611497297902>()) {
                  case 1974710892:
                     break label76;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"s3vzjy15txiwgj","+wRdOrlJtijcFyXa06ed/ofVW+4GTg+Y78ePcgpBd2A=",-5367006142945789971,-1747195382403353576,-963751231993744535,8835166074892396938>()) {
            case -207288577:
               break;
            default:
               throw null;
         }
      }

      String var1 = var10000;
      if (!var1.contains(
         (String)com.yiyiaddon.m.b.a<"soi0byripoua","p/T5/d8IWJeTy29VqTDHh5Rq+9fdlKlEq0zzpbCa",4149795658186852681,-3615072248646404162,-854784781652431,-2281496085658343384>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m1sfg468f6l6","LPCxI0pie6nTKRHcrQ7st1vQEYlzqWCdGrAESAMS2bI=",7678718528459612554,-5261354476038783046,8611038980687070003,3372551888857801266>()) {
            case 1899226620:
               if (!var1.contains(
                  (String)com.yiyiaddon.m.b.a<"szb01wy3v37v9","aMtLEI25TZTMMMbF3wMrZZKNWKHhKcCLvcOAHDNI2E7rjPYqAordYw==",-7139749263457274066,6042500079379149343,4369196334407939236,8503421527268831367>()
               )) {
                  if (!var1.contains(
                     (String)com.yiyiaddon.m.b.a<"s8h6u12fcitxe","Lr19x1uwyfVPv+9T/wRAGvvh28MTGaF3sBM0tfvc",8464135820668136142,8934693976676741595,-6207867503639410453,760876119093823814>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s11lthialaznv1","8YsAXR8XEpSYHonDafaDCokCmoKHUK7PkTQbWdqU9fs=",-1843906894241169594,-1566352697915864264,-6137532555346938020,885918981853761512>()) {
                        case 1293967792:
                           if (!var1.contains(
                              (String)com.yiyiaddon.m.b.a<"s32skrh6u21z26","BcrbEcUFerW6snIU96YB0Jb8GkhZjZYU+6y9vtHOpgIS2ltV6dI5fA==",-3454912739270276985,8597645581553722231,-5999900655492457789,-7989704138337849472>()
                           )) {
                              if (!var1.contains(
                                 (String)com.yiyiaddon.m.b.a<"s20nzt9199o5sn","0SUozeetXoM1UXejW/qEZybx/D+Fqae+pudJP0pb",-3036220150652579548,2993186794367486849,8329136257645088752,693591545118200291>()
                              )) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2gvlr9do0hc04","a5vKfaukr6YKP+HGXC14f4eTqleSrIwO56hx0GY4Z7Y=",-9029816200482643321,-3264493085578116844,-8695281523381827626,585436566583985787>()) {
                                    case 1603438935:
                                       if (!var1.contains(
                                          (String)com.yiyiaddon.m.b.a<"s1mj53sw1ahjx3","l7Pjzr+xk+C3mRxp29CYmlwbH9Jl1DAYAb00zfq0D2gKm+RRb3uHgg==",3601442013630164342,604294152949879676,-4934423686641320085,-48534417478007027>()
                                       )) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s33rdl4bxkhmdt","gMY1XMkRZJm/AbEbvRTGe1YfEcRq0sXEAizZD3tGZpY=",-6390079097359366462,-3385567559794478474,8240263617781917103,3387331669750475695>()) {
                                             case 1741566966:
                                                if (!var1.contains(
                                                   (String)com.yiyiaddon.m.b.a<"s1cklp8duld3dt","/U3zdUuK5vzFVgEn5CksZ5qYz2CNL99gLeTRXA0lVSJc4j56",5733098347037245099,-4058945442029769277,-8400738798861051017,3223430485777714854>()
                                                )) {
                                                   if (!var1.contains(
                                                      (String)com.yiyiaddon.m.b.a<"s3oio3mzeo42ic","qiV/Lg4X/1SMe2a52vHcQxRkY0swRnaeFedz073Z",-6605765055036887769,-7931619410365589816,-8162777674133981560,2386313522252548061>()
                                                   )) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2hdgie6gmjqgh","qD0xJ8Sw8AiNzFu6IFDP2WZxyGtpklkC3ClIU3U7qeM=",4005489659656880375,-4479540514043903562,3679086470101057403,7982221427262549246>()) {
                                                         case 1576168663:
                                                            if (!var1.contains(
                                                               (String)com.yiyiaddon.m.b.a<"s3qza0b0m8ow5s","l5aZVShuIBcHQOLKT2Q9ZM5lbBTbl/BjqWqp1j0H84D/MZR9vD2Ecw==",7835198551297442761,97084833920097113,8101554512325739352,-8941695514974078267>()
                                                            )) {
                                                               return com.yiyiaddon.e.n.n.f.d.UNKNOWN;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"sjw1xeecro00r","svDS2V6CWd8DkxHe3uiVTwOndqfzfDSMbdEVtvxCCeg=",-3010105532931711911,4332114718415417530,4841987707836584934,-2049193426148064156>()) {
                                                               case 311409730:
                                                                  return com.yiyiaddon.e.n.n.f.d.WINTER;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   return com.yiyiaddon.e.n.n.f.d.WINTER;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s34vs25690eju6","54bIF5+dZjOf63SeudYOKr2nlpvmgzXsImP0lKqTihA=",16606878265397827,2317445528695326973,4727335840721332127,-2324620764575695434>()) {
                                                   case -1663626302:
                                                      return com.yiyiaddon.e.n.n.f.d.AUTUMN;
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

                              return com.yiyiaddon.e.n.n.f.d.AUTUMN;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s46r44cjssef5","GmjfGOAFK/yGMW/SHFUIJ6GbYjm853EOtTXOGwC897M=",-207967586954154827,3786830791737288543,3234224279293488780,-9081523284147723132>()) {
                              case -1049744931:
                                 return com.yiyiaddon.e.n.n.f.d.SUMMER;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return com.yiyiaddon.e.n.n.f.d.SUMMER;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s29pxfyimsh3is","CSbzohwNV8QdubVXdryzPqCUquG24s0n+q6Qut9TyU8=",5219989195648495365,1043060620795970426,-968580741181194794,-1537184718174348046>()) {
                     case 101502640:
                        return com.yiyiaddon.e.n.n.f.d.SPRING;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.n.f.d.SPRING;
      }
   }

   public record a(String tY, int mU) {
      public String el() {
         return String.format(
            (String)com.yiyiaddon.m.b.a<"s3s9c139r8goay","kqUcQhU/u3MvS6XqX6Dcu4DgTlnrRMzorHek+fSW4VqpfIH/x6ycPA==",4341750710784653320,-5290658704271837425,-5960212962158997726,-3443568480144096987>(),
            this.mU
         );
      }

      public String em() {
         return this.tY;
      }

      public int cy() {
         return this.mU;
      }
   }

   private record b(int mV, int mW) {
      public int cz() {
         return this.mV;
      }

      public int cA() {
         return this.mW;
      }
   }

   public enum c {
      ALLOWED,
      DISALLOWED,
      UNKNOWN;
   }

   public enum d {
      SPRING(
         (String)com.yiyiaddon.m.b.a<"s2fshliwbs1d6s","8Nx2olvhZTgaPSqi++2TQBc81nrLa0orilMfs72k",7675243958267840669,-8788826385589927989,-4412618231308791490,2258754909631545524>()
      ),
      SUMMER(
         (String)com.yiyiaddon.m.b.a<"skb1fmby9597e","YxIUMuDsD3Fbq5UGekMTzQYPAVwtdVV983Wgpvdj",1450205422384143150,-3158328394875251787,8644607217857946777,-6601346230414297038>()
      ),
      AUTUMN(
         (String)com.yiyiaddon.m.b.a<"s2nbhmoui2kpmt","JNz49v55R/WLOa25jRrEgQLd2B/d1czrGclTz+mm",6133592877169260670,2105670576583991781,261386469539904560,3825379963782605919>()
      ),
      WINTER(
         (String)com.yiyiaddon.m.b.a<"s21zw6b812bv0u","VsHwCZ/DsuC5dSBAEIEVtOXJqpJOrAUl397q/Xjb",6556382824070557115,-4790374860820905259,-7442526445460370021,417371254938410944>()
      ),
      UNKNOWN(
         (String)com.yiyiaddon.m.b.a<"s2fejr32lt6hnh","64OurVieE6YMR+udP0utO2zYVeZaRmJvl66ki9MOEKE=",8250822717092433659,-6123184669165870955,7006646070762929659,-6599420101602730264>()
      );

      private final String tZ;

      d(String var3) {
         this.tZ = var3;
      }

      public String m() {
         return this.tZ;
      }
   }

   public record e(String ua, String ub, Set<com.yiyiaddon.e.n.n.f.f> O, String uc, String ud, long J) {
      public e(String ua, String ub, Set<com.yiyiaddon.e.n.n.f.f> O, String uc, String ud, long J) {
         O = O == null ? Set.of() : Set.copyOf(O);
         this.ua = ua;
         this.ub = ub;
         this.O = O;
         this.uc = uc;
         this.ud = ud;
         this.J = J;
      }

      public boolean dz() {
         if (!this.O.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s349mxhn9x081k","isT9O/kGLfAjr1VrwlcZUVTwnJ79gO2/GU3xOlopBpk=",1396375030564299166,-7632146750088939356,-5853205094523656067,-5867082017803839260>()) {
               case -750400248:
                  switch ((int)com.yiyiaddon.m.b.a<"sarsr7ii4u7uf","qbuKABEu9ixPqQ7Ju5T0B4kl0dTfxb+4caZgPRkxAeA=",6988193966850375413,-7185387957503871809,-5282612068681953844,-5728547557925117594>()) {
                     case -1928591079:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s179s2say6667s","ZN5Iu7C25nw5IpcHmvsqIjDQlY1BZG+0nPj/y6fRVSM=",-1380711927172598174,-5921512896508380532,-8580423651031715348,-4953525228647191583>()) {
               case 791499904:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public String m() {
         Iterator var1 = this.O.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sfph68bo1n1m2","vPfC+iEqHMUPOA2d3Sa5S/Oc3zV3GVQLWtt6SpEgVsI=",2649243474137608847,4928835635524980415,6841283176845730619,6537665710552593639>()) {
            case 1450707568:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3nnmfeljeto03","1cur7z/hnNATdEgtU72oB4PyBTTZuGeeu0vHhF+PzSA=",-7534703548116417733,-6917245956796441820,-5316251670458888696,-4084202546722425171>()) {
                     case -2083542814:
                        com.yiyiaddon.e.n.n.f.f var2 = (com.yiyiaddon.e.n.n.f.f)var1.next();
                        if (var2.a() != com.yiyiaddon.e.n.n.f.d.UNKNOWN) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1ci3e5lqz7kzv","aAw9zCHe6wWuVpkYpWNvNdHop3JT5Qk9ydtUtBmnthI=",170115355666648228,2160498660130688803,7312178781070327645,2453252923807148344>()) {
                              case -20147795:
                                 return var2.a().m();
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1ocoddodwybmi","unIqppIC6PcltmwKkVDi4+jcL7nU49uC6ENmPQxt7KQ=",-7347458349745139981,3735693171573503061,2014465817038092124,-360029765987689524>()) {
                           case -244275914:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.O.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"syeokp9a1tc96","9h3em8P06y8tD4FdEY02tyw4JQubehIdRU05yCUVkpw=",-1891119208469664217,-8476022027235143820,-2627701603338559248,-2840967626442414025>()) {
                     case -1058783073:
                        String var10000 = (String)com.yiyiaddon.m.b.a<"s2p82oka6eaams","1vAykondUIT7jw28M2BxRnZjfePpHz2EyAIvRIe7L+I=",-2770215579863317887,-3225558419076452135,6509907692815137792,5865621461669966529>();
                        switch ((int)com.yiyiaddon.m.b.a<"s52eube0nsasp","ThjHmH2uuYYS48WLz/cLBNmpvEdfjpGVa+fANuQqbSU=",-3335473471500465358,7850166572712615545,8569869250503756321,304504615204242148>()) {
                           case 970283262:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var3 = (String)com.yiyiaddon.m.b.a<"s3m8wzxbmgurzi","Qrqio+oheFiOQ9Z0i9n6UTl2oHJITGMT+L387ZY06Gr8bRFy",-4776374687645378089,5031420355301648759,4062192936949306531,584123654884801872>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1gno2amvj1mz","ULUDNPLtb6XT+b9wuv0EUnzVoIEIJrDSdup1OZeZi2c=",-8860043332938965774,1071044618987046854,-7421429488277783650,8541911380312915535>()) {
                     case -1846494187:
                        return var3;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }

      public String bT() {
         return this.ua;
      }

      public String dn() {
         return this.ub;
      }

      public Set<com.yiyiaddon.e.n.n.f.f> u() {
         return this.O;
      }

      public String dG() {
         return this.uc;
      }

      public String en() {
         return this.ud;
      }

      public long o() {
         return this.J;
      }
   }

   public record f(String ue, com.yiyiaddon.e.n.n.f.d b, String uf) {
      public String L() {
         return this.ue;
      }

      public com.yiyiaddon.e.n.n.f.d a() {
         return this.b;
      }

      public String m() {
         return this.uf;
      }
   }

   public record g(Set<com.yiyiaddon.e.n.n.f.f> P, String ug) {
      public g(Set<com.yiyiaddon.e.n.n.f.f> P, String ug) {
         P = P == null ? Set.of() : Set.copyOf(P);
         this.P = P;
         this.ug = ug;
      }

      public boolean dz() {
         if (!this.P.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1hr240rzp4frw","T93IIwifbHZPiBUsQGnTKWNd9KzdmgAdP7EAsz85i0c=",-6465282909466307200,3107479997949992943,-6444303541763692369,-3126476968831781483>()) {
               case 1250145866:
                  switch ((int)com.yiyiaddon.m.b.a<"sgba24kvdgn9e","Otg5/dCClFm/jz+3W3EnoosUlGI81XS0iTarTAO3DKw=",6455081725590234685,4027572426509125869,4527594847575545232,-1703435898206479244>()) {
                     case 1505855293:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3t9mflyic3ju3","C1dLricX4m4revvXr/CLd4vFxNHR3oEf8pyN9ss3Lkk=",-503831543537943343,4454074334875127941,-3073170839846228767,7358049348796259038>()) {
               case 224338118:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public Set<com.yiyiaddon.e.n.n.f.f> v() {
         return this.P;
      }

      public String dG() {
         return this.ug;
      }
   }
}
