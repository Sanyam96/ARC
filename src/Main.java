package src;

import src.arc.ArcCacheAlgo;
import src.models.PageModel;

import java.util.Scanner;

/*
* Main class of the Adaptive Replacement Cache Algorithm
* */
public class Main {

    public static void main(String[] args) {
        TestCase1();
        TestCase2();
        TestCase3();
    }

//    TestCase1
    public static void TestCase1(){

        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the size of Cache: ");
        int cSize = scn.nextInt();
//        cSize = 2;

        ArcCacheAlgo pageArcCache = new ArcCacheAlgo(cSize);

        PageModel p1 = new PageModel(1, 101);
        PageModel p2 = new PageModel(2, 102);
        PageModel p3 = new PageModel(3, 103);
        PageModel p4 = new PageModel(4, 104);
        PageModel p5 = new PageModel(5, 105);
        PageModel p6 = new PageModel(6, 106);

        pageArcCache.cache(p1.getPageId(), p1);
        pageArcCache.cache(p2.getPageId(), p2);
        pageArcCache.cache(p3.getPageId(), p3);
        pageArcCache.cache(p4.getPageId(), p4);
        pageArcCache.cache(p5.getPageId(), p5);
        pageArcCache.cache(p6.getPageId(), p6);

        pageArcCache.printCacheIdsFromQueue();
    }

//    TestCase 2
    public static void TestCase2(){

        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the size of Cache: ");
        int cSize = scn.nextInt();
//        cSize = 3;

        ArcCacheAlgo pageArcCache = new ArcCacheAlgo(cSize);

        PageModel p1 = new PageModel(1, 401);
        PageModel p2 = new PageModel(7, 402);
        PageModel p3 = new PageModel(10, 403);
        PageModel p4 = new PageModel(4, 404);
        PageModel p5 = new PageModel(5, 405);
        PageModel p6 = new PageModel(6, 406);

        pageArcCache.cache(p1.getPageId(), p1);
        pageArcCache.cache(p2.getPageId(), p2);
        pageArcCache.cache(p3.getPageId(), p3);
        pageArcCache.cache(p4.getPageId(), p4);
        pageArcCache.cache(p5.getPageId(), p5);
        pageArcCache.cache(p6.getPageId(), p6);

        pageArcCache.printCacheIdsFromQueue();
    }

//    TestCase3
    public static void TestCase3(){

        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the size of Cache: ");
        int cSize = scn.nextInt();
//        cSize = 1;

        ArcCacheAlgo pageArcCache = new ArcCacheAlgo(cSize);

        PageModel p1 = new PageModel(101, 1101);
        PageModel p2 = new PageModel(102, 1102);
        PageModel p3 = new PageModel(103, 1103);
        PageModel p4 = new PageModel(104, 1104);
        PageModel p5 = new PageModel(105, 1105);
        PageModel p6 = new PageModel(106, 1106);

        pageArcCache.cache(p1.getPageId(), p1);
        pageArcCache.cache(p2.getPageId(), p2);
        pageArcCache.cache(p3.getPageId(), p3);
        pageArcCache.cache(p4.getPageId(), p4);
        pageArcCache.cache(p5.getPageId(), p5);
        pageArcCache.cache(p6.getPageId(), p6);

        pageArcCache.printCacheIdsFromQueue();
    }
}
