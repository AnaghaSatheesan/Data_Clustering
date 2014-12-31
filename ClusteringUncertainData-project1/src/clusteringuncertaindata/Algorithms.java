/*
 * To change this template, choose Tools | Templates
 * and open the template : the editor.
 */
package clusteringuncertaindata;

import java.lang.Double;
import java.lang.String;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Chandramouliswaran
 */
public class Algorithms {

    public static AlgorithmCallback callback;

    public static Double KMeans(Vector<Double> P) {
        double result = 0.0d;

        double[] centroids = new double[4];

        Double[] p_ = new Double[P.size()];

        p_ = P.toArray(p_);

        for (int i = 0; i < centroids.length; i++) {
            centroids[i] = p_[(int) ((double) p_.length * (double) Math.random())];
        }


        for (int k = 0; k < p_.length; k++) {
            double sigma = 0.0d;

            for (int j = 0; j < k; j++) {
                double sigma2 = 0.0d;

                for (int h = 0; h < centroids.length; h++) {
                    sigma2 += Math.pow(p_[j] - centroids[h], 2);
                }

                sigma += sigma2;
            }

            p_[k] = sigma;
        }

        double min = Double.MAX_VALUE;
        for (int i = 0; i < p_.length; i++) {
            if (p_[i].isNaN() || p_[i].isInfinite()) {
                p_[i] = 1.0d;
            }

            if (p_[i] < min) {
                min = p_[i];
            }
        }

        result = min;

        return result;
    }

    public static Double KLDivergence(Vector<Double> P, Vector<Double> Q) {
        Double result = new Double(0.0);

        Vector<Double> pq = new Vector<Double>();

        Vector<Double> P_ = new Vector<Double>();
        Vector<Double> Q_ = new Vector<Double>();


        for (int h = 0; h < P.size(); h++) {
            double left = 0.0d;

            for (int d = 0; d < h; d++) {
                double mul = 0.0d;

                for (int j = 0; j < d; j++) {
                    mul *= P.elementAt(j);
                }

                left = (double) (1 / ((((P.size()) * Math.pow(2 * Math.PI, d / 2)))) * mul);

                double sum = 0.0d;

                for (int k = 0; k < d; k++) {
                    mul = 0.0d;
                    for (int j = 0; j < d; j++) {
                        mul *= Math.pow(Math.E, -((j - (P.elementAt(k) * P.elementAt(k))) / ((2 * j) * (2 * j))));
                    }
                    sum += mul;
                }

                P_.add(left * sum);
            }
        }

//            for(int h=0;h<Q.size();h++) {
//                double left = 0.0d;
//                
//                for(int d=0;d<h;d++) {
//                    double mul = 0.0d;
//                    
//                    for(int j=0;j<d;j++) {
//                        mul *= Q.elementAt(j);
//                    }
//                    
//                    left = (double) (1/((((Q.size())*Math.pow(2*Math.PI,d/2))))*mul);
//                    
//                    double sum = 0.0d;
//                    
//                    for(int k=0;k<d;k++) {
//                      mul = 0.0d;
//                      for(int j=0;j<d;j++) {
//                          mul *= Math.pow(Math.E , -((j-(Q.elementAt(k)*Q.elementAt(k)))/((2*j)*(2*j))));
//                      }
//                      sum += mul;
//                    }
//                    
//                    Q_.add(left * sum);
//                }
//            }

        Q_ = P_;

        for (Double p : P_) {
            for (Double q : Q_) {
                pq.add(Math.E * (Math.log(p / q)));
            }
        }

        Double sigma = 1.0d;

        for (int s = 1; s < (P_.size() + Q_.size()); s++) {
            Double sum = 0.0d;

            for (int i = 0; i < s; i++) {
                sum += Math.abs(pq.elementAt(i));
            }

            sigma += Math.log((sum == 0.0d || sum.isNaN()) ? 1.0d : sum);

            sigma = (Double) ((Double) ((Double) 1.0d / (Double) (new Double(s)))) * sigma;
        }



        result = sigma;

        return result;
    }
    public static long tcPClusteringStart, tcPClusteringEnd, tcPClusteringRun;
    public static long scPClusteringStart, scPClusteringEnd, scPClusteringRun;
    public static long tcUClusteringStart, tcUClusteringEnd, tcUClusteringRun;
    public static long scUClusteringStart, scUClusteringEnd, scUClusteringRun;
    public static long tcKClusteringStart, tcKClusteringEnd, tcKClusteringRun;
    public static long scKClusteringStart, scKClusteringEnd, scKClusteringRun;
    public static Double precisionPartitionClustering, recallPartitionClustering;
    public static Double precisionDensityClustering, recallDensityClustering;
    public static Double precisionKMeansClustering, recallKMeansClustering;                                                         

    public static Object[] PartitionClustering(Vector<String[]> items) {
        Vector<String[]> partitioned = new Vector<String[]>();

        tcPClusteringStart = new Date().getTime();
        scPClusteringStart = items.size();

        Hashtable<String, Vector<String[]>> partitions = new Hashtable<String, Vector<String[]>>();

        for (String[] item : items) {
            for (String item1 : item) {
                for (String[] item2 : items) {
                    for (String item3 : item2) {
                        if (item1.equals(item3)) {
                            Vector<String[]> partitions2 = null;

                            if (partitions.containsKey(item1)) {
                                partitions2 = partitions.get(item1);

                                partitions2.add(item2);

                                partitions.put(item1, partitions2);
                            } else {
                                partitions2 = new Vector<String[]>();

                                partitions2.add(item2);

                                partitions.put(item1, partitions2);
                            }
                        } else {
                        }
                    }
                }
            }
        }


        Hashtable<String, Double> pq = new Hashtable<String, Double>();

        Hashtable<String, Vector<Double>> pq1 = new Hashtable<String, Vector<Double>>();

        Vector<Double> PQ = new Vector<Double>();

        Iterator<String> items2 = partitions.keySet().iterator();

        for (; items2.hasNext();) {
            String item1 = items2.next();
            Vector<String[]> partitions2 = partitions.get(item1);

            PQ.add((Double) (new Double(partitions2.size()).doubleValue() / new Double(partitions.size()).doubleValue()));

            Vector<Double> pqs = new Vector<Double>();

            pqs.addAll(PQ);

            pq1.put(item1, pqs);
        }

        Iterator<String> items3 = partitions.keySet().iterator();

        int count = 0;

        for (; items3.hasNext();) {
            double progress = (double) ((double) count / (double) partitions.keySet().size());

            if (callback != null) {
                callback.updateProgress(progress);
            }

            String item1 = items3.next();

            Iterator<String> items4 = partitions.keySet().iterator();
            while (items4.hasNext()) {
                String item2 = items4.next();
                if (!item2.equals(item1)) {

                    pq.put(item1, KLDivergence(pq1.get(item1), pq1.get(item2)));
                }
            }

            count++;
        }


        Double[] pqv = new Double[pq.size()];
        pqv = pq.values().toArray(pqv);

        Vector<String[]> cluster = new Vector<String[]>();

        Double average = 0.0d;

        for (int i = 0; i < pqv.length; i++) {
            average += pqv[i];
        }

        average = average / pqv.length;

        Double[] uki = new Double[pqv.length];

        for (int i = 0; i < pqv.length; i++) {
            uki[i] = pqv[i];
        }

        Double[] dki = new Double[pqv.length];
        Double x = 0.0d, e = 1.0d;

        for (int i = 0; i < pqv.length; i++) {
            x += pqv[i];
        }

        x = x / pqv.length;

        for (int i = 0; i < pqv.length; i++) {
            e *= pqv[i];
        }

        e = e / pqv.length;

        for (int i = 0; i < pqv.length; i++) {
            Double sum = 0.0d;

            for (int j = 0; j < pqv.length; j++) {
                sum += (x * pqv[j] + e * pqv[j]);
            }

            sum = sum / pqv[i];

            dki[i] = sum;
        }

        Double[] jst = new Double[pqv.length];

        int n = pqv.length;
        int c = dki.length;

        for (int k = 0; k < n; k++) {
            Double sum = 0.0d;

            for (int i = 0; i < c; i++) {
                sum += Math.pow(uki[i], pqv.length) * dki[i];
            }

            jst[k] = sum / k;
        }




        Arrays.sort(pqv);

        for (Double v : pqv) {
            for (String[] item : items) {
                for (String item1 : item) {
                    if (pq.get(item1).doubleValue() == v.doubleValue()) {
                        if (!Clustercontains(cluster, item)) {
                            cluster.add(item);
                        }
                    }
                }
            }
        }



        Vector<String[]> TP = new Vector<String[]>();
        Vector<String[]> FP = new Vector<String[]>();
        Vector<String[]> FS = new Vector<String[]>();

        for (String[] G : items) {
            int index = 0;

            for (String[] C : cluster) {
                boolean equals = false;

                for (String c1 : C) {
                    for (String g : G) {
                        if (c1.equals(g)) {
                            equals = true;
                        } else {
                            equals = false;
                        }
                    }
                }

                if (equals) {
                    TP.add(C);
                } else {
                    FP.add(C);
                    FS.add(G);
                }
            }
        }

        Double precision = (Double) (new Double(TP.size()).doubleValue() / (new Double(TP.size()).doubleValue() + new Double(FP.size()).doubleValue()));
        Double recall = (Double) (new Double(TP.size()).doubleValue() / (new Double(TP.size()).doubleValue() + new Double(FS.size()).doubleValue()));

        precisionPartitionClustering = precision;
        recallPartitionClustering = recall;

        tcPClusteringEnd = new Date().getTime();
        tcPClusteringRun = tcPClusteringEnd - tcPClusteringStart;

        scPClusteringEnd = pq.size();
        scPClusteringRun = scPClusteringEnd - scPClusteringStart;

        if (scPClusteringStart < scPClusteringEnd) {
            scPClusteringRun = scPClusteringEnd - scPClusteringStart;
        } else {
            scPClusteringRun = scPClusteringStart - scPClusteringEnd;
        }

        partitioned = cluster;

        return new Object[]{partitions, partitioned, average, jst, uki, dki};
    }

    public static Object[] DensityClustering(Vector<String[]> items) {
        Vector<String[]> result = new Vector<String[]>();

        tcUClusteringStart = new Date().getTime();
        scUClusteringStart = items.size();

        Hashtable<String, Vector<String[]>> partitions = new Hashtable<String, Vector<String[]>>();

        for (String[] item : items) {
            for (String item1 : item) {
                for (String[] item2 : items) {
                    for (String item3 : item2) {
                        if (item1.equals(item3)) {
                            Vector<String[]> partitions2 = null;

                            if (partitions.containsKey(item1)) {
                                partitions2 = partitions.get(item1);

                                partitions2.add(item2);

                                partitions.put(item1, partitions2);
                            } else {
                                partitions2 = new Vector<String[]>();

                                partitions2.add(item2);

                                partitions.put(item1, partitions2);
                            }
                        } else {
                        }
                    }
                }
            }
        }


        Hashtable<String, Double> pq = new Hashtable<String, Double>();

        Hashtable<String, Vector<Double>> pq1 = new Hashtable<String, Vector<Double>>();

        Vector<Double> PQ = new Vector<Double>();

        Iterator<String> items3 = partitions.keySet().iterator();
        for (; items3.hasNext();) {
            String item1 = items3.next();

            Vector<String[]> partitions2 = partitions.get(item1);

            PQ.add((Double) (new Double(partitions2.size()).doubleValue() / new Double(partitions.size()).doubleValue()));

            Vector<Double> pqs = new Vector<Double>();

            pqs.addAll(PQ);

            pq1.put(item1, pqs);
        }

        items3 = partitions.keySet().iterator();

        int count = 0;
        for (; items3.hasNext();) {
            double progress = (double) ((double) count / (double) partitions.keySet().size());

            if (callback != null) {
                callback.updateProgress(progress);
            }

            String item1 = items3.next();

            Iterator<String> items4 = partitions.keySet().iterator();
            
            while (items4.hasNext()) {
                String item2 = items4.next();
                if (!item2.equals(item1)) {

                    pq.put(item1, KLDivergence(pq1.get(item1), pq1.get(item2)));
                }
            }
            count++;
        }

        Double min = Double.MAX_VALUE;

        String[] c1 = null;

        Double[] cq = new Double[pq.values().size()];
        cq = pq.values().toArray(cq);

        for (Double cq1 : cq) {
            if (cq1 < min) {
                min = cq1;
            }
        }

        items3 = pq.keySet().iterator();

        for (; items3.hasNext();) {
            String item1 = items3.next();
            if (pq.get(item1).doubleValue() == min.doubleValue()) {
                for (String[] item : items) {
                    for (String item2 : item) {
                        if (item2.equals(item1)) {
                            c1 = item;
                            break;
                        }
                    }

                    if (c1 != null) {
                        break;
                    }
                }
            }
            if (c1 != null) {
                break;
            }
        }

        Hashtable<String, Double> dps = new Hashtable<String, Double>();

        for (String[] item : items) {
            for (String item1 : item) {
                Double dp = pq.get(item1);
                Double sum = 0.0d;

                for (String[] item2 : items) {
                    for (String item3 : item2) {
                        Double dpi = pq.get(item3);
                        sum += (dpi - dp);
                    }
                }

                if (!dps.containsKey(item1)) {
                    dps.put(item1, sum);
                }
            }
        }

        Double[] dpsv = new Double[dps.values().size()];
        dpsv = dps.values().toArray(dpsv);

        Arrays.sort(dpsv);

        try {
            for (int i = 0; i < dpsv.length / 2; i++) {
                Double temp = dpsv[i];
                dpsv[i] = dpsv[dpsv.length - i];
                dpsv[dpsv.length - i] = temp;
            }
        } catch (Exception e) {
        }


        for (Double dpsv1 : dpsv) {
            for (String[] item : items) {
                for (String item1 : item) {
                    if (dps.get(item1).doubleValue() == dpsv1.doubleValue()) {
                        if (!Clustercontains(result, item)) {
                            result.add(item);
                        }
                    }
                }
            }
        }

        Double[] pqv = new Double[pq.size()];
        pqv = pq.values().toArray(pqv);

        Vector<String[]> cluster = new Vector<String[]>();

        Double average = 0.0d;

        for (int i = 0; i < pqv.length; i++) {
            average += pqv[i];
        }

        average = average / pqv.length;

        Double[] uki = new Double[pqv.length];

        for (int i = 0; i < pqv.length; i++) {
            uki[i] = pqv[i];
        }

        Double[] dki = new Double[pqv.length];
        Double x = 0.0d, e = 1.0d;

        for (int i = 0; i < pqv.length; i++) {
            x += pqv[i];
        }

        x = x / pqv.length;

        for (int i = 0; i < pqv.length; i++) {
            e *= pqv[i];
        }

        e = e / pqv.length;

        for (int i = 0; i < pqv.length; i++) {
            Double sum = 0.0d;

            for (int j = 0; j < pqv.length; j++) {
                sum += (x * pqv[j] + e * pqv[j]);
            }

            sum = sum / pqv[i];

            dki[i] = sum;
        }

        Double[] jst = new Double[pqv.length];

        int n = pqv.length;
        int c = dki.length;

        for (int k = 0; k < n; k++) {
            Double sum = 0.0d;

            for (int i = 0; i < c; i++) {
                sum += Math.pow(uki[i], pqv.length) * dki[i];
            }

            jst[k] = sum / k;
        }




        Arrays.sort(pqv);

        for (Double v : pqv) {
            for (String[] item : items) {
                for (String item1 : item) {
                    if (pq.get(item1).doubleValue() == v.doubleValue()) {
                        if (!Clustercontains(cluster, item)) {
                            cluster.add(item);
                        }
                    }
                }
            }
        }



        Vector<String[]> TP = new Vector<String[]>();
        Vector<String[]> FP = new Vector<String[]>();
        Vector<String[]> FS = new Vector<String[]>();

        for (String[] G : items) {
            int index = 0;

            for (String[] C : result) {
                boolean equals = false;

                for (String c2 : C) {
                    for (String g : G) {
                        if (c2.equals(g)) {
                            equals = true;
                        } else {
                            equals = false;
                        }
                    }
                }

                if (equals) {
                    TP.add(C);
                } else {
                    FP.add(C);
                    FS.add(G);
                }
            }
        }

        Double precision = (Double) (new Double(TP.size()).doubleValue() / (new Double(TP.size()).doubleValue() + new Double(FP.size()).doubleValue()));
        Double recall = (Double) (new Double(TP.size()).doubleValue() / (new Double(TP.size()).doubleValue() + new Double(FS.size()).doubleValue()));

        precisionDensityClustering = precision;
        recallDensityClustering = recall;


        tcUClusteringEnd = new Date().getTime();
        scUClusteringEnd = dps.size();



        tcUClusteringRun = tcUClusteringEnd - tcUClusteringStart;
        scUClusteringRun = scUClusteringEnd - scUClusteringStart;

        if (scUClusteringStart < scUClusteringEnd) {
            scUClusteringRun = scUClusteringEnd - scUClusteringStart;
        } else {
            scUClusteringRun = scUClusteringStart - scUClusteringEnd;
        }

        return new Object[]{partitions, result, average, jst, uki, dki};
    }

    public static Object[] KMeansClustering(Vector<String[]> items) {
        Vector<String[]> result = new Vector<String[]>();

        tcKClusteringStart = new Date().getTime();
        scKClusteringStart = items.size();

        Hashtable<String, Vector<String[]>> partitions = new Hashtable<String, Vector<String[]>>();

        for (String[] item : items) {
            for (String item1 : item) {
                for (String[] item2 : items) {
                    for (String item3 : item2) {
                        if (item1.equals(item3)) {
                            Vector<String[]> partitions2 = null;

                            if (partitions.containsKey(item1)) {
                                partitions2 = partitions.get(item1);

                                partitions2.add(item2);

                                partitions.put(item1, partitions2);
                            } else {
                                partitions2 = new Vector<String[]>();

                                partitions2.add(item2);

                                partitions.put(item1, partitions2);
                            }
                        } else {
                        }
                    }
                }
            }
        }


        Hashtable<String, Double> pq = new Hashtable<String, Double>();

        Hashtable<String, Vector<Double>> pq1 = new Hashtable<String, Vector<Double>>();

        Vector<Double> PQ = new Vector<Double>();

        Iterator<String> items3 = partitions.keySet().iterator();
        for (; items3.hasNext();) {
            String item1 = items3.next();

            Vector<String[]> partitions2 = partitions.get(item1);

            PQ.add((Double) (new Double(partitions2.size()).doubleValue() / new Double(partitions.size()).doubleValue()));

            Vector<Double> pqs = new Vector<Double>();

            pqs.addAll(PQ);

            pq1.put(item1, pqs);
        }

        items3 = partitions.keySet().iterator();

        int count = 0;
        for (; items3.hasNext();) {
            double progress = (double) ((double) count / (double) partitions.keySet().size());

            if (callback != null) {
                callback.updateProgress(progress);
            }

            String item1 = items3.next();
            pq.put(item1, KMeans(pq1.get(item1)));
            count++;
        }

        Double min = Double.MAX_VALUE;

        String[] c1 = null;

        Double[] cq = new Double[pq.values().size()];
        cq = pq.values().toArray(cq);

        for (Double cq1 : cq) {
            if (cq1 < min) {
                min = cq1;
            }
        }

        items3 = pq.keySet().iterator();

        for (; items3.hasNext();) {
            String item1 = items3.next();
            if (pq.get(item1).doubleValue() == min.doubleValue()) {
                for (String[] item : items) {
                    for (String item2 : item) {
                        if (item2.equals(item1)) {
                            c1 = item;
                            break;
                        }
                    }

                    if (c1 != null) {
                        break;
                    }
                }
            }
            if (c1 != null) {
                break;
            }
        }

        Hashtable<String, Double> dps = new Hashtable<String, Double>();

        for (String[] item : items) {
            for (String item1 : item) {
                Double dp = pq.get(item1);
                Double sum = 0.0d;

                for (String[] item2 : items) {
                    for (String item3 : item2) {
                        Double dpi = pq.get(item3);
                        sum += (dpi - dp);
                    }
                }

                if (!dps.containsKey(item1)) {
                    dps.put(item1, sum);
                }
            }
        }

        Double[] dpsv = new Double[dps.values().size()];
        dpsv = dps.values().toArray(dpsv);

        Arrays.sort(dpsv);

        try {
            for (int i = 0; i < dpsv.length / 2; i++) {
                Double temp = dpsv[i];
                dpsv[i] = dpsv[dpsv.length - i];
                dpsv[dpsv.length - i] = temp;
            }
        } catch (Exception e) {
        }


        for (Double dpsv1 : dpsv) {
            for (String[] item : items) {
                for (String item1 : item) {
                    if (dps.get(item1).doubleValue() == dpsv1.doubleValue()) {
                        if (!Clustercontains(result, item)) {
                            result.add(item);
                        }
                    }
                }
            }
        }

        Double[] pqv = new Double[pq.size()];
        pqv = pq.values().toArray(pqv);

        Vector<String[]> cluster = new Vector<String[]>();

        Double average = 0.0d;

        for (int i = 0; i < pqv.length; i++) {
            average += pqv[i];
        }

        average = average / pqv.length;

        Double[] uki = new Double[pqv.length];

        for (int i = 0; i < pqv.length; i++) {
            uki[i] = pqv[i];
        }

        Double[] dki = new Double[pqv.length];
        Double x = 0.0d, e = 1.0d;

        for (int i = 0; i < pqv.length; i++) {
            x += pqv[i];
        }

        x = x / pqv.length;

        for (int i = 0; i < pqv.length; i++) {
            e *= pqv[i];
        }

        e = e / pqv.length;

        for (int i = 0; i < pqv.length; i++) {
            Double sum = 0.0d;

            for (int j = 0; j < pqv.length; j++) {
                sum += (x * pqv[j] + e * pqv[j]);
            }

            sum = sum / pqv[i];

            dki[i] = sum;
        }

        Double[] jst = new Double[pqv.length];

        int n = pqv.length;
        int c = dki.length;

        for (int k = 0; k < n; k++) {
            Double sum = 0.0d;

            for (int i = 0; i < c; i++) {
                sum += Math.pow(uki[i], pqv.length) * dki[i];
            }

            jst[k] = sum / k;
        }




        Arrays.sort(pqv);

        for (Double v : pqv) {
            for (String[] item : items) {
                for (String item1 : item) {
                    if (pq.get(item1).doubleValue() == v.doubleValue()) {
                        if (!Clustercontains(cluster, item)) {
                            cluster.add(item);
                        }
                    }
                }
            }
        }



        Vector<String[]> TP = new Vector<String[]>();
        Vector<String[]> FP = new Vector<String[]>();
        Vector<String[]> FS = new Vector<String[]>();

        for (String[] G : items) {
            int index = 0;

            for (String[] C : result) {
                boolean equals = false;

                for (String c2 : C) {
                    for (String g : G) {
                        if (c2.equals(g)) {
                            equals = true;
                        } else {
                            equals = false;
                        }
                    }
                }

                if (equals) {
                    TP.add(C);
                } else {
                    FP.add(C);
                    FS.add(G);
                }
            }
        }

        Double precision = (Double) (new Double(TP.size()).doubleValue() / (new Double(TP.size()).doubleValue() + new Double(FP.size()).doubleValue()));
        Double recall = (Double) (new Double(TP.size()).doubleValue() / (new Double(TP.size()).doubleValue() + new Double(FS.size()).doubleValue()));

        precisionKMeansClustering = precision;
        recallKMeansClustering = recall;

        tcKClusteringEnd = new Date().getTime();
        scKClusteringEnd = dps.size();



        tcKClusteringRun = tcKClusteringEnd - tcKClusteringStart;
        scKClusteringRun = scKClusteringEnd - scKClusteringStart;

        if (scKClusteringStart < scKClusteringEnd) {
            scKClusteringRun = scKClusteringEnd - scKClusteringStart;
        } else {
            scKClusteringRun = scKClusteringStart - scKClusteringEnd;
        }

        return new Object[]{partitions, result, average, jst, uki, dki};
    }

    public static Double ValidityXIEBenis(Double[] jst, Double[] uki, Double[] dki, Double average) {
        Double[] TFi = new Double[uki.length];
        Double x = 0.0d, e = 1.0d;

        for (int i = 0; i < jst.length; i++) {
            x += Double.isInfinite(jst[i].doubleValue()) || jst[i].isNaN() ? 1.0d : jst[i].doubleValue();
        }

        x = x / jst.length;

        for (int i = 0; i < jst.length; i++) {
            e += Double.isInfinite(jst[i].doubleValue()) || jst[i].isNaN() ? 1.0d : jst[i].doubleValue();
        }

        e = e / jst.length;


        for (int i = 0; i < TFi.length; i++) {
            Double sigma = 0.0d;

            int n = uki.length;

            for (int k = 0; k < n; k++) {
                sigma += Math.pow(uki[k], uki.length) * ((x * uki[k] + e * uki[k]) - jst[i]) * Math.pow((x * uki[k] + e * uki[k]) - jst[i], 2);
            }

            Double sigma2 = 0.0d;

            for (int k = 0; k < n; k++) {
                sigma2 += Math.pow(uki[k], uki.length);
            }

            sigma = ((sigma.isInfinite() || sigma.isNaN()) ? 1.0d : sigma);

            TFi[i] = sigma / sigma2;
        }

        Double TXB = 0.0d;

        int n1 = TFi.length;
        int c = jst.length;

        Double sigma3 = 0.0d;

        for (int k = 0; k < n1; k++) {
            Double sigma = 0.0d;

            for (int i = 0; i < c; i++) {
                sigma += Math.pow(uki[i], 1) * Math.pow((x * uki[i] + e * dki[i]), 1);

            }

            sigma3 += sigma.isInfinite() || sigma.isNaN() ? 1.0d : sigma;
        }

        Double min = Double.MAX_VALUE;

        for (int i = 0; i < TFi.length; i++) {
            if (Math.pow(TFi[i].doubleValue(), 1) < min) {
                min = Math.pow(TFi[i].doubleValue(), 1);
            }
        }

        TXB = 1 / sigma3 + 1 / (min.isInfinite() || min.isNaN() ? 2.0d : min);

        return TXB.isInfinite() || TXB.isNaN() ? TXB += Math.abs(TXB) : TXB;
    }

    public static Double ValidityFukuyamaSugenos(Double[] jst, Double[] uki, Double[] dki, Double average) {
        Double[] TFi = new Double[uki.length];

        Double x = 0.0d, e = 1.0d;

        for (int i = 0; i < jst.length; i++) {
            x += (jst[i].isInfinite() || jst[i].isNaN()) ? 1.0d : jst[i].doubleValue();
        }

        x = x / jst.length;

        for (int i = 0; i < jst.length; i++) {
            e += (jst[i].isInfinite() || jst[i].isNaN()) ? 1.0d : jst[i].doubleValue();
        }

        e = e / jst.length;


        for (int i = 0; i < TFi.length; i++) {
            Double sigma = 0.0d;

            int n = uki.length;

            for (int k = 0; k < n; k++) {
                sigma += Math.pow(uki[k], uki.length) * ((x * uki[k] + e * uki[k]) - jst[i]) * Math.pow((x * uki[k] + e * uki[k]) - jst[i], 2);
            }

            Double sigma2 = 0.0d;

            for (int k = 0; k < n; k++) {
                sigma2 += Math.pow(uki[k], uki.length);
            }

            sigma = (sigma.isInfinite() || sigma.isNaN()) ? 1.0d : sigma;

            TFi[i] = sigma / sigma2;
        }

        Double v = 0.0d;

        int n1 = TFi.length;

        for (int k = 0; k < n1; k++) {
            v += (x * TFi[k] + e * TFi[k]);
        }

        v = (1 / n1) * (Double.isNaN(v.doubleValue()) ? 1.0d : v.doubleValue());

        Double TFS = 0.0d;
        int c = jst.length;


        for (int k = 0; k < n1; k++) {
            Double sigma = 0.0d;

            for (int i = 0; i < c; i++) {
                sigma += Math.pow(uki[i], 1) * (Math.pow((x * uki[k] + e * dki[k]) - v, 1));
            }

            sigma = sigma.isInfinite() || sigma.isNaN() ? 1.0d : sigma;

            TFS += sigma;
        }

        return TFS;
    }

    private static boolean Clustercontains(Vector<String[]> cluster, String[] item) {

        boolean contains = false;

        for (String[] item1 : cluster) {
            String item2 = "";

            for (String item3 : item1) {
                item2 += item3 + "_";
            }

            String item3 = "";

            for (String item4 : item) {
                item3 += item4 + "_";
            }

            if (item2.equals(item3)) {
                contains = true;
                break;
            }
        }

        return contains;
    }
}
