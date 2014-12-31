/*
 * ClusteringUncertainDataView.java
 */
package clusteringuncertaindata;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 * The application's main frame.
 */
public class ClusteringUncertainDataView extends FrameView {

    boolean partitionClosed = false;
    boolean densityClosed = false;
    boolean kmeansClosed = false;
    boolean partitionApplied = false;
    boolean densityApplied = false;
    boolean kmeansApplied = false;

    public ClusteringUncertainDataView(SingleFrameApplication app) {
        super(app);

        initComponents();

        jMenuItemPartitionClustering.setEnabled(false);
        jMenuItemDensityClustering.setEnabled(false);
        jMenuItemKMeansClustering.setEnabled(false);
        jMenuItemPrecisionRecalls.setEnabled(false);
        jMenuItemValidity.setEnabled(false);
        jMenuItemComparisonChart.setEnabled(false);
        jMenuItemWhetherDatabase.setEnabled(false);
        jMenuItemLogout.setEnabled(false);

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = ClusteringUncertainDataApp.getApplication().getMainFrame();
            aboutBox = new ClusteringUncertainDataAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        ClusteringUncertainDataApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jInternalFrameWheatherDatabase = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableWheatherDatabase = new javax.swing.JTable();
        jInternalFramePartitionClustering = new javax.swing.JInternalFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePartitionClustering = new javax.swing.JTable();
        jInternalFrameProgress = new javax.swing.JInternalFrame();
        jProgressBarProgress = new javax.swing.JProgressBar();
        jInternalFrameDensityClustering = new javax.swing.JInternalFrame();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableDensityClustering = new javax.swing.JTable();
        jInternalFrameKMeansClustering = new javax.swing.JInternalFrame();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableKMeansClustering = new javax.swing.JTable();
        jInternalFramePrecisionRecalls = new javax.swing.JInternalFrame();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTablePrecisionRecalls = new javax.swing.JTable();
        jInternalFrameValidity = new javax.swing.JInternalFrame();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTableValidity = new javax.swing.JTable();
        jInternalFrameComparisonChart = new javax.swing.JInternalFrame();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableComparisonChart = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemTimeComplexity = new javax.swing.JMenuItem();
        jMenuItemSpaceComplexity = new javax.swing.JMenuItem();
        jInternalFrameTimeComplexityChart = new javax.swing.JInternalFrame();
        jInternalFrameSpaceComplexityChart = new javax.swing.JInternalFrame();
        jInternalFrameLogin = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jButtonLogin = new javax.swing.JButton();
        jInternalFrameRegister = new javax.swing.JInternalFrame();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldRegisterName = new javax.swing.JTextField();
        jTextFieldRegisterEmail = new javax.swing.JTextField();
        jTextFieldRegisterDesignation = new javax.swing.JTextField();
        jButtonRegister = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldRegisterUsername = new javax.swing.JTextField();
        jPasswordFieldRegisterPassword = new javax.swing.JPasswordField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItemPartitionClustering = new javax.swing.JMenuItem();
        jMenuItemDensityClustering = new javax.swing.JMenuItem();
        jMenuItemKMeansClustering = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemPrecisionRecalls = new javax.swing.JMenuItem();
        jMenuItemValidity = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemComparisonChart = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemWhetherDatabase = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItemLogin = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItemLogout = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jDesktopPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jDesktopPane1.setDoubleBuffered(true);
        jDesktopPane1.setName("jDesktopPane1"); // NOI18N

        jInternalFrameWheatherDatabase.setClosable(true);
        jInternalFrameWheatherDatabase.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameWheatherDatabase.setIconifiable(true);
        jInternalFrameWheatherDatabase.setMaximizable(true);
        jInternalFrameWheatherDatabase.setResizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(clusteringuncertaindata.ClusteringUncertainDataApp.class).getContext().getResourceMap(ClusteringUncertainDataView.class);
        jInternalFrameWheatherDatabase.setTitle(resourceMap.getString("jInternalFrameWheatherDatabase.title")); // NOI18N
        jInternalFrameWheatherDatabase.setName("jInternalFrameWheatherDatabase"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTableWheatherDatabase.setBackground(resourceMap.getColor("jTableWheatherDatabase.background")); // NOI18N
        jTableWheatherDatabase.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableWheatherDatabase.setForeground(resourceMap.getColor("jTableWheatherDatabase.foreground")); // NOI18N
        jTableWheatherDatabase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableWheatherDatabase.setName("jTableWheatherDatabase"); // NOI18N
        jScrollPane2.setViewportView(jTableWheatherDatabase);

        javax.swing.GroupLayout jInternalFrameWheatherDatabaseLayout = new javax.swing.GroupLayout(jInternalFrameWheatherDatabase.getContentPane());
        jInternalFrameWheatherDatabase.getContentPane().setLayout(jInternalFrameWheatherDatabaseLayout);
        jInternalFrameWheatherDatabaseLayout.setHorizontalGroup(
            jInternalFrameWheatherDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
            .addGroup(jInternalFrameWheatherDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
        );
        jInternalFrameWheatherDatabaseLayout.setVerticalGroup(
            jInternalFrameWheatherDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
            .addGroup(jInternalFrameWheatherDatabaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
        );

        jInternalFrameWheatherDatabase.setBounds(80, 70, 610, 370);
        jDesktopPane1.add(jInternalFrameWheatherDatabase, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFramePartitionClustering.setClosable(true);
        jInternalFramePartitionClustering.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFramePartitionClustering.setIconifiable(true);
        jInternalFramePartitionClustering.setMaximizable(true);
        jInternalFramePartitionClustering.setTitle(resourceMap.getString("jInternalFramePartitionClustering.title")); // NOI18N
        jInternalFramePartitionClustering.setName("jInternalFramePartitionClustering"); // NOI18N
        jInternalFramePartitionClustering.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFramePartitionClusteringInternalFrameClosing(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTablePartitionClustering.setBackground(resourceMap.getColor("jTablePartitionClustering.background")); // NOI18N
        jTablePartitionClustering.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTablePartitionClustering.setForeground(resourceMap.getColor("jTablePartitionClustering.foreground")); // NOI18N
        jTablePartitionClustering.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePartitionClustering.setName("jTablePartitionClustering"); // NOI18N
        jScrollPane3.setViewportView(jTablePartitionClustering);

        javax.swing.GroupLayout jInternalFramePartitionClusteringLayout = new javax.swing.GroupLayout(jInternalFramePartitionClustering.getContentPane());
        jInternalFramePartitionClustering.getContentPane().setLayout(jInternalFramePartitionClusteringLayout);
        jInternalFramePartitionClusteringLayout.setHorizontalGroup(
            jInternalFramePartitionClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jInternalFramePartitionClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
        );
        jInternalFramePartitionClusteringLayout.setVerticalGroup(
            jInternalFramePartitionClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jInternalFramePartitionClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );

        jInternalFramePartitionClustering.setBounds(180, 140, 550, 330);
        jDesktopPane1.add(jInternalFramePartitionClustering, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameProgress.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameProgress.setIconifiable(true);
        jInternalFrameProgress.setMaximizable(true);
        jInternalFrameProgress.setResizable(true);
        jInternalFrameProgress.setTitle(resourceMap.getString("jInternalFrameProgress.title")); // NOI18N
        jInternalFrameProgress.setName("jInternalFrameProgress"); // NOI18N
        jInternalFrameProgress.setVisible(true);

        jProgressBarProgress.setName("jProgressBarProgress"); // NOI18N
        jInternalFrameProgress.getContentPane().add(jProgressBarProgress, java.awt.BorderLayout.CENTER);

        jInternalFrameProgress.setBounds(800, 10, 250, 80);
        jDesktopPane1.add(jInternalFrameProgress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameDensityClustering.setClosable(true);
        jInternalFrameDensityClustering.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameDensityClustering.setIconifiable(true);
        jInternalFrameDensityClustering.setMaximizable(true);
        jInternalFrameDensityClustering.setResizable(true);
        jInternalFrameDensityClustering.setTitle(resourceMap.getString("jInternalFrameDensityClustering.title")); // NOI18N
        jInternalFrameDensityClustering.setName("jInternalFrameDensityClustering"); // NOI18N
        jInternalFrameDensityClustering.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrameDensityClusteringInternalFrameClosing(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTableDensityClustering.setBackground(resourceMap.getColor("jTableDensityClustering.background")); // NOI18N
        jTableDensityClustering.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableDensityClustering.setForeground(resourceMap.getColor("jTableDensityClustering.foreground")); // NOI18N
        jTableDensityClustering.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableDensityClustering.setName("jTableDensityClustering"); // NOI18N
        jScrollPane4.setViewportView(jTableDensityClustering);

        javax.swing.GroupLayout jInternalFrameDensityClusteringLayout = new javax.swing.GroupLayout(jInternalFrameDensityClustering.getContentPane());
        jInternalFrameDensityClustering.getContentPane().setLayout(jInternalFrameDensityClusteringLayout);
        jInternalFrameDensityClusteringLayout.setHorizontalGroup(
            jInternalFrameDensityClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jInternalFrameDensityClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
        );
        jInternalFrameDensityClusteringLayout.setVerticalGroup(
            jInternalFrameDensityClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jInternalFrameDensityClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );

        jInternalFrameDensityClustering.setBounds(180, 140, 550, 330);
        jDesktopPane1.add(jInternalFrameDensityClustering, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameKMeansClustering.setClosable(true);
        jInternalFrameKMeansClustering.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameKMeansClustering.setIconifiable(true);
        jInternalFrameKMeansClustering.setMaximizable(true);
        jInternalFrameKMeansClustering.setTitle(resourceMap.getString("jInternalFrameKMeansClustering.title")); // NOI18N
        jInternalFrameKMeansClustering.setName("jInternalFrameKMeansClustering"); // NOI18N
        jInternalFrameKMeansClustering.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrameKMeansClusteringInternalFrameClosing(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTableKMeansClustering.setBackground(resourceMap.getColor("jTableKMeansClustering.background")); // NOI18N
        jTableKMeansClustering.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableKMeansClustering.setForeground(resourceMap.getColor("jTableKMeansClustering.foreground")); // NOI18N
        jTableKMeansClustering.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableKMeansClustering.setName("jTableKMeansClustering"); // NOI18N
        jScrollPane5.setViewportView(jTableKMeansClustering);

        javax.swing.GroupLayout jInternalFrameKMeansClusteringLayout = new javax.swing.GroupLayout(jInternalFrameKMeansClustering.getContentPane());
        jInternalFrameKMeansClustering.getContentPane().setLayout(jInternalFrameKMeansClusteringLayout);
        jInternalFrameKMeansClusteringLayout.setHorizontalGroup(
            jInternalFrameKMeansClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jInternalFrameKMeansClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
        );
        jInternalFrameKMeansClusteringLayout.setVerticalGroup(
            jInternalFrameKMeansClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jInternalFrameKMeansClusteringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );

        jInternalFrameKMeansClustering.setBounds(180, 140, 550, 330);
        jDesktopPane1.add(jInternalFrameKMeansClustering, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFramePrecisionRecalls.setClosable(true);
        jInternalFramePrecisionRecalls.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFramePrecisionRecalls.setIconifiable(true);
        jInternalFramePrecisionRecalls.setMaximizable(true);
        jInternalFramePrecisionRecalls.setTitle(resourceMap.getString("jInternalFramePrecisionRecalls.title")); // NOI18N
        jInternalFramePrecisionRecalls.setName("jInternalFramePrecisionRecalls"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTablePrecisionRecalls.setBackground(resourceMap.getColor("jTablePrecisionRecalls.background")); // NOI18N
        jTablePrecisionRecalls.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTablePrecisionRecalls.setForeground(resourceMap.getColor("jTablePrecisionRecalls.foreground")); // NOI18N
        jTablePrecisionRecalls.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePrecisionRecalls.setName("jTablePrecisionRecalls"); // NOI18N
        jScrollPane6.setViewportView(jTablePrecisionRecalls);

        javax.swing.GroupLayout jInternalFramePrecisionRecallsLayout = new javax.swing.GroupLayout(jInternalFramePrecisionRecalls.getContentPane());
        jInternalFramePrecisionRecalls.getContentPane().setLayout(jInternalFramePrecisionRecallsLayout);
        jInternalFramePrecisionRecallsLayout.setHorizontalGroup(
            jInternalFramePrecisionRecallsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jInternalFramePrecisionRecallsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
        );
        jInternalFramePrecisionRecallsLayout.setVerticalGroup(
            jInternalFramePrecisionRecallsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jInternalFramePrecisionRecallsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );

        jInternalFramePrecisionRecalls.setBounds(180, 140, 550, 330);
        jDesktopPane1.add(jInternalFramePrecisionRecalls, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameValidity.setClosable(true);
        jInternalFrameValidity.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameValidity.setIconifiable(true);
        jInternalFrameValidity.setMaximizable(true);
        jInternalFrameValidity.setTitle(resourceMap.getString("jInternalFrameValidity.title")); // NOI18N
        jInternalFrameValidity.setName("jInternalFrameValidity"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jTableValidity.setBackground(resourceMap.getColor("jTableValidity.background")); // NOI18N
        jTableValidity.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableValidity.setForeground(resourceMap.getColor("jTableValidity.foreground")); // NOI18N
        jTableValidity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableValidity.setName("jTableValidity"); // NOI18N
        jScrollPane7.setViewportView(jTableValidity);

        javax.swing.GroupLayout jInternalFrameValidityLayout = new javax.swing.GroupLayout(jInternalFrameValidity.getContentPane());
        jInternalFrameValidity.getContentPane().setLayout(jInternalFrameValidityLayout);
        jInternalFrameValidityLayout.setHorizontalGroup(
            jInternalFrameValidityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jInternalFrameValidityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
        );
        jInternalFrameValidityLayout.setVerticalGroup(
            jInternalFrameValidityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(jInternalFrameValidityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );

        jInternalFrameValidity.setBounds(180, 140, 550, 330);
        jDesktopPane1.add(jInternalFrameValidity, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameComparisonChart.setClosable(true);
        jInternalFrameComparisonChart.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameComparisonChart.setIconifiable(true);
        jInternalFrameComparisonChart.setMaximizable(true);
        jInternalFrameComparisonChart.setTitle(resourceMap.getString("jInternalFrameComparisonChart.title")); // NOI18N
        jInternalFrameComparisonChart.setName("jInternalFrameComparisonChart"); // NOI18N

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jTableComparisonChart.setBackground(resourceMap.getColor("jTableComparisonChart.background")); // NOI18N
        jTableComparisonChart.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableComparisonChart.setForeground(resourceMap.getColor("jTableComparisonChart.foreground")); // NOI18N
        jTableComparisonChart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableComparisonChart.setName("jTableComparisonChart"); // NOI18N
        jScrollPane8.setViewportView(jTableComparisonChart);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu3.setText(resourceMap.getString("jMenu3.text")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        jMenuItemTimeComplexity.setText(resourceMap.getString("jMenuItemTimeComplexity.text")); // NOI18N
        jMenuItemTimeComplexity.setName("jMenuItemTimeComplexity"); // NOI18N
        jMenuItemTimeComplexity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTimeComplexityActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemTimeComplexity);

        jMenuItemSpaceComplexity.setText(resourceMap.getString("jMenuItemSpaceComplexity.text")); // NOI18N
        jMenuItemSpaceComplexity.setName("jMenuItemSpaceComplexity"); // NOI18N
        jMenuItemSpaceComplexity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSpaceComplexityActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemSpaceComplexity);

        jMenuBar1.add(jMenu3);

        jInternalFrameComparisonChart.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout jInternalFrameComparisonChartLayout = new javax.swing.GroupLayout(jInternalFrameComparisonChart.getContentPane());
        jInternalFrameComparisonChart.getContentPane().setLayout(jInternalFrameComparisonChartLayout);
        jInternalFrameComparisonChartLayout.setHorizontalGroup(
            jInternalFrameComparisonChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
            .addGroup(jInternalFrameComparisonChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
        );
        jInternalFrameComparisonChartLayout.setVerticalGroup(
            jInternalFrameComparisonChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
            .addGroup(jInternalFrameComparisonChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
        );

        jInternalFrameComparisonChart.setBounds(180, 140, 550, 330);
        jDesktopPane1.add(jInternalFrameComparisonChart, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameTimeComplexityChart.setClosable(true);
        jInternalFrameTimeComplexityChart.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameTimeComplexityChart.setIconifiable(true);
        jInternalFrameTimeComplexityChart.setMaximizable(true);
        jInternalFrameTimeComplexityChart.setResizable(true);
        jInternalFrameTimeComplexityChart.setTitle(resourceMap.getString("jInternalFrameTimeComplexityChart.title")); // NOI18N
        jInternalFrameTimeComplexityChart.setName("jInternalFrameTimeComplexityChart"); // NOI18N
        jInternalFrameTimeComplexityChart.setBounds(50, 70, 430, 300);
        jDesktopPane1.add(jInternalFrameTimeComplexityChart, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameSpaceComplexityChart.setClosable(true);
        jInternalFrameSpaceComplexityChart.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameSpaceComplexityChart.setIconifiable(true);
        jInternalFrameSpaceComplexityChart.setMaximizable(true);
        jInternalFrameSpaceComplexityChart.setResizable(true);
        jInternalFrameSpaceComplexityChart.setTitle(resourceMap.getString("jInternalFrameSpaceComplexityChart.title")); // NOI18N
        jInternalFrameSpaceComplexityChart.setName("jInternalFrameSpaceComplexityChart"); // NOI18N
        jInternalFrameSpaceComplexityChart.setBounds(50, 70, 430, 300);
        jDesktopPane1.add(jInternalFrameSpaceComplexityChart, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameLogin.setClosable(true);
        jInternalFrameLogin.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameLogin.setTitle(resourceMap.getString("jInternalFrameLogin.title")); // NOI18N
        jInternalFrameLogin.setName("jInternalFrameLogin"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextFieldUsername.setName("jTextFieldUsername"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jPasswordFieldPassword.setName("jPasswordFieldPassword"); // NOI18N

        jButtonLogin.setText(resourceMap.getString("jButtonLogin.text")); // NOI18N
        jButtonLogin.setName("jButtonLogin"); // NOI18N
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrameLoginLayout = new javax.swing.GroupLayout(jInternalFrameLogin.getContentPane());
        jInternalFrameLogin.getContentPane().setLayout(jInternalFrameLoginLayout);
        jInternalFrameLoginLayout.setHorizontalGroup(
            jInternalFrameLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameLoginLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jInternalFrameLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(59, 59, 59)
                .addGroup(jInternalFrameLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordFieldPassword)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrameLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogin)
                .addGap(243, 243, 243))
        );
        jInternalFrameLoginLayout.setVerticalGroup(
            jInternalFrameLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameLoginLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jInternalFrameLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jInternalFrameLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jButtonLogin)
                .addContainerGap())
        );

        jInternalFrameLogin.setBounds(100, 70, 300, 210);
        jDesktopPane1.add(jInternalFrameLogin, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jInternalFrameRegister.setClosable(true);
        jInternalFrameRegister.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jInternalFrameRegister.setName("jInternalFrameRegister"); // NOI18N
        jInternalFrameRegister.setPreferredSize(new java.awt.Dimension(502, 350));
        jInternalFrameRegister.setRequestFocusEnabled(false);

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextFieldRegisterName.setName("jTextFieldRegisterName"); // NOI18N

        jTextFieldRegisterEmail.setName("jTextFieldRegisterEmail"); // NOI18N

        jTextFieldRegisterDesignation.setName("jTextFieldRegisterDesignation"); // NOI18N

        jButtonRegister.setText(resourceMap.getString("jButtonRegister.text")); // NOI18N
        jButtonRegister.setName("jButtonRegister"); // NOI18N
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jTextFieldRegisterUsername.setName("jTextFieldRegisterUsername"); // NOI18N

        jPasswordFieldRegisterPassword.setName("jPasswordFieldRegisterPassword"); // NOI18N

        javax.swing.GroupLayout jInternalFrameRegisterLayout = new javax.swing.GroupLayout(jInternalFrameRegister.getContentPane());
        jInternalFrameRegister.getContentPane().setLayout(jInternalFrameRegisterLayout);
        jInternalFrameRegisterLayout.setHorizontalGroup(
            jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameRegisterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(47, 47, 47)
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrameRegisterLayout.createSequentialGroup()
                        .addComponent(jButtonRegister)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrameRegisterLayout.createSequentialGroup()
                        .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPasswordFieldRegisterPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jTextFieldRegisterUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(jTextFieldRegisterDesignation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldRegisterName, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                                .addComponent(jTextFieldRegisterEmail)))
                        .addGap(198, 198, 198))))
        );
        jInternalFrameRegisterLayout.setVerticalGroup(
            jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameRegisterLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldRegisterName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldRegisterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldRegisterDesignation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRegisterUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(29, 29, 29)
                .addGroup(jInternalFrameRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jPasswordFieldRegisterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87)
                .addComponent(jButtonRegister)
                .addGap(29, 29, 29))
        );

        jInternalFrameRegister.setBounds(20, 20, 510, 400);
        jDesktopPane1.add(jInternalFrameRegister, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jScrollPane1.setViewportView(jDesktopPane1);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1060, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 859, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE))
        );

        menuBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItemPartitionClustering.setText(resourceMap.getString("jMenuItemPartitionClustering.text")); // NOI18N
        jMenuItemPartitionClustering.setName("jMenuItemPartitionClustering"); // NOI18N
        jMenuItemPartitionClustering.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPartitionClusteringActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemPartitionClustering);

        jMenuItemDensityClustering.setText(resourceMap.getString("jMenuItemDensityClustering.text")); // NOI18N
        jMenuItemDensityClustering.setName("jMenuItemDensityClustering"); // NOI18N
        jMenuItemDensityClustering.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDensityClusteringActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemDensityClustering);

        jMenuItemKMeansClustering.setText(resourceMap.getString("jMenuItemKMeansClustering.text")); // NOI18N
        jMenuItemKMeansClustering.setName("jMenuItemKMeansClustering"); // NOI18N
        jMenuItemKMeansClustering.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemKMeansClusteringActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemKMeansClustering);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        jMenuItemPrecisionRecalls.setText(resourceMap.getString("jMenuItemPrecisionRecalls.text")); // NOI18N
        jMenuItemPrecisionRecalls.setName("jMenuItemPrecisionRecalls"); // NOI18N
        jMenuItemPrecisionRecalls.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPrecisionRecallsActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemPrecisionRecalls);

        jMenuItemValidity.setText(resourceMap.getString("jMenuItemValidity.text")); // NOI18N
        jMenuItemValidity.setName("jMenuItemValidity"); // NOI18N
        jMenuItemValidity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemValidityActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemValidity);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(clusteringuncertaindata.ClusteringUncertainDataApp.class).getContext().getActionMap(ClusteringUncertainDataView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        jMenuItemComparisonChart.setText(resourceMap.getString("jMenuItemComparisonChart.text")); // NOI18N
        jMenuItemComparisonChart.setName("jMenuItemComparisonChart"); // NOI18N
        jMenuItemComparisonChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemComparisonChartActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemComparisonChart);

        menuBar.add(jMenu2);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItemWhetherDatabase.setText(resourceMap.getString("jMenuItemWhetherDatabase.text")); // NOI18N
        jMenuItemWhetherDatabase.setName("jMenuItemWhetherDatabase"); // NOI18N
        jMenuItemWhetherDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemWhetherDatabaseActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemWhetherDatabase);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        jMenu4.setText(resourceMap.getString("jMenu4.text")); // NOI18N
        jMenu4.setName("jMenu4"); // NOI18N
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });

        jMenuItemLogin.setText(resourceMap.getString("jMenuItemLogin.text")); // NOI18N
        jMenuItemLogin.setName("jMenuItemLogin"); // NOI18N
        jMenuItemLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoginActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemLogin);

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItemLogout.setText(resourceMap.getString("jMenuItemLogout.text")); // NOI18N
        jMenuItemLogout.setName("jMenuItemLogout"); // NOI18N
        jMenuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogoutActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemLogout);

        menuBar.add(jMenu4);

        statusPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 886, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemWhetherDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemWhetherDatabaseActionPerformed


        DBUtils.connect();
        final Vector<String[]> items = DBUtils.getItems();


        this.jTableWheatherDatabase.setModel(new AbstractTableModel() {

            private String[] columns = new String[]{"TEMPERATURE", "PRECIPITATION", "HUMIDITY", "WINDSPEED", "DIRECTION"};

            public int getRowCount() {
                return items.size();
            }

            public int getColumnCount() {
                return this.columns.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return items.elementAt(rowIndex)[columnIndex];
            }

            @Override
            public String getColumnName(int column) {
                return this.columns[column];
            }
        });

        this.jInternalFrameWheatherDatabase.setVisible(true);

    }//GEN-LAST:event_jMenuItemWhetherDatabaseActionPerformed
    private Object[] partitionClusteringResult = null;
    private Object[] densityClusteringResult = null;
    private Object[] kmeansClusteringResult = null;

    private void jMenuItemPartitionClusteringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPartitionClusteringActionPerformed


        DBUtils.connect();
        final Vector<String[]> items = DBUtils.getItems();

        Algorithms.callback = new AlgorithmCallback() {

            public void updateProgress(double progress) {
                jProgressBarProgress.setValue(0);
                final double progess1 = progress;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jProgressBarProgress.setValue((int) (progess1 * 100));

                        jProgressBarProgress.invalidate();
                        jProgressBarProgress.setValue(0);
                        jProgressBarProgress.updateUI();
                    }
                });
            }
        };


        Thread threadClustering = new Thread(new Runnable() {

            public void run() {
                Object[] clustered = Algorithms.PartitionClustering(items);

                partitionClusteringResult = clustered;

                Vector<String[]> items2 = (Vector<String[]>) clustered[1];
                Hashtable<String, Vector<String[]>> partitions = (Hashtable<String, Vector<String[]>>) clustered[0];


                final double progess1 = 1.0d;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jProgressBarProgress.setValue((int) (progess1 * 100));
                        jProgressBarProgress.invalidate();
                        jProgressBarProgress.updateUI();
                    }
                });


                Iterator<String> keys = partitions.keySet().iterator();

                final Vector<String[]> clustered2 = new Vector<String[]>();

                int index = 1;
                while (keys.hasNext()) {
                    String key = keys.next();

                    Vector<String[]> clusterItems = partitions.get(key);

                    clustered2.add(new String[]{"Cluster:[" + (index++) + "]", "", "", "", ""});

                    for (String[] clusterItem : clusterItems) {
                        boolean contains = false;

                        if (clustered2.contains(clusterItem)) {
                            contains = true;
                        }

                        if (!contains) {
                            clustered2.add(clusterItem);
                        }

                    }
                }

                jTablePartitionClustering.setModel(new AbstractTableModel() {

                    private String[] columns = new String[]{"TEMPERATURE", "PRECIPITATION", "HUMIDITY", "WINDSPEED", "DIRECTION"};

                    public int getRowCount() {
                        return clustered2.size();
                    }

                    public int getColumnCount() {
                        return this.columns.length;
                    }

                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return clustered2.elementAt(rowIndex)[columnIndex];
                    }

                    @Override
                    public String getColumnName(int column) {
                        return this.columns[column];
                    }
                });

                jInternalFramePartitionClustering.setVisible(true);
                partitionApplied = true;
                if (partitionApplied && densityApplied && kmeansApplied) {
                    jMenuItemPrecisionRecalls.setEnabled(true);
                    jMenuItemValidity.setEnabled(true);
                    jMenuItemComparisonChart.setEnabled(true);
                }
            }
        });

        threadClustering.start();


    }//GEN-LAST:event_jMenuItemPartitionClusteringActionPerformed

    private void jMenuItemDensityClusteringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDensityClusteringActionPerformed

        DBUtils.connect();
        final Vector<String[]> items = DBUtils.getItems();

        Algorithms.callback = new AlgorithmCallback() {

            public void updateProgress(double progress) {

                final double progess1 = progress;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jProgressBarProgress.setValue((int) (progess1 * 100));
                        jProgressBarProgress.invalidate();
                        jProgressBarProgress.setValue(0);
                        jProgressBarProgress.updateUI();
                    }
                });
            }
        };


        Thread threadClustering = new Thread(new Runnable() {

            public void run() {
                Object[] clustered = Algorithms.DensityClustering(items);

                densityClusteringResult = clustered;

                Vector<String[]> items2 = (Vector<String[]>) clustered[1];
                Hashtable<String, Vector<String[]>> partitions = (Hashtable<String, Vector<String[]>>) clustered[0];


                final double progess1 = 1.0d;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jProgressBarProgress.setValue((int) (progess1 * 100));
                        jProgressBarProgress.invalidate();
                        jProgressBarProgress.updateUI();
                    }
                });


                Iterator<String> keys = partitions.keySet().iterator();

                final Vector<String[]> clustered2 = new Vector<String[]>();

                int index = 1;
                while (keys.hasNext()) {
                    String key = keys.next();

                    Vector<String[]> clusterItems = partitions.get(key);

                    clustered2.add(new String[]{"Cluster:[" + (index++) + "]", "", "", "", ""});

                    for (String[] clusterItem : clusterItems) {
                        boolean contains = false;

                        if (clustered2.contains(clusterItem)) {
                            contains = true;
                        }

                        if (!contains) {
                            clustered2.add(clusterItem);
                        }

                    }
                }

                jTableDensityClustering.setModel(new AbstractTableModel() {

                    private String[] columns = new String[]{"TEMPERATURE", "PRECIPITATION", "HUMIDITY", "WINDSPEED", "DIRECTION"};

                    public int getRowCount() {
                        return clustered2.size();
                    }

                    public int getColumnCount() {
                        return this.columns.length;
                    }

                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return clustered2.elementAt(rowIndex)[columnIndex];
                    }

                    @Override
                    public String getColumnName(int column) {
                        return this.columns[column];
                    }
                });

                jInternalFrameDensityClustering.setVisible(true);
                densityApplied = true;
                if (partitionApplied && densityApplied && kmeansApplied) {
                    jMenuItemPrecisionRecalls.setEnabled(true);
                    jMenuItemValidity.setEnabled(true);
                    jMenuItemComparisonChart.setEnabled(true);
                }
            }
        });

        threadClustering.start();


    }//GEN-LAST:event_jMenuItemDensityClusteringActionPerformed

    private void jMenuItemKMeansClusteringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemKMeansClusteringActionPerformed

        DBUtils.connect();
        final Vector<String[]> items = DBUtils.getItems();

        Algorithms.callback = new AlgorithmCallback() {

            public void updateProgress(double progress) {
                final double progess1 = progress;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jProgressBarProgress.setValue((int) (progess1 * 100));
                        jProgressBarProgress.invalidate();
                        jProgressBarProgress.setValue(0);
                        jProgressBarProgress.updateUI();
                    }
                });
            }
        };


        Thread threadClustering = new Thread(new Runnable() {

            public void run() {
                Object[] clustered = Algorithms.KMeansClustering(items);

                kmeansClusteringResult = clustered;

                Vector<String[]> items2 = (Vector<String[]>) clustered[1];
                Hashtable<String, Vector<String[]>> partitions = (Hashtable<String, Vector<String[]>>) clustered[0];


                final double progess1 = 1.0d;
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jProgressBarProgress.setValue((int) (progess1 * 100));
                        jProgressBarProgress.invalidate();
                        jProgressBarProgress.updateUI();
                    }
                });


                Iterator<String> keys = partitions.keySet().iterator();

                final Vector<String[]> clustered2 = new Vector<String[]>();

                int index = 1;
                while (keys.hasNext()) {
                    String key = keys.next();

                    Vector<String[]> clusterItems = partitions.get(key);

                    clustered2.add(new String[]{"Cluster:[" + (index++) + "]", "", "", "", ""});

                    for (String[] clusterItem : clusterItems) {
                        boolean contains = false;

                        if (clustered2.contains(clusterItem)) {
                            contains = true;
                        }

                        if (!contains) {
                            clustered2.add(clusterItem);
                        }

                    }
                }

                jTableKMeansClustering.setModel(new AbstractTableModel() {

                    private String[] columns = new String[]{"TEMPERATURE", "PRECIPITATION", "HUMIDITY", "WINDSPEED", "DIRECTION"};

                    public int getRowCount() {
                        return clustered2.size();
                    }

                    public int getColumnCount() {
                        return this.columns.length;
                    }

                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return clustered2.elementAt(rowIndex)[columnIndex];
                    }

                    @Override
                    public String getColumnName(int column) {
                        return this.columns[column];
                    }
                });

                jInternalFrameKMeansClustering.setVisible(true);
                kmeansApplied = true;
                if (partitionApplied && densityApplied && kmeansApplied) {
                    jMenuItemPrecisionRecalls.setEnabled(true);
                    jMenuItemValidity.setEnabled(true);
                    jMenuItemComparisonChart.setEnabled(true);
                }
            }
        });

        threadClustering.start();


    }//GEN-LAST:event_jMenuItemKMeansClusteringActionPerformed

    private void jMenuItemPrecisionRecallsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPrecisionRecallsActionPerformed


        final Vector<String[]> prResults = new Vector<String[]>();

        prResults.add(new String[]{"Partition Based Clustering", ""});
        prResults.add(new String[]{"" + Algorithms.precisionPartitionClustering, "" + Algorithms.recallPartitionClustering});

        prResults.add(new String[]{"Density Based Clustering", ""});
        prResults.add(new String[]{"" + Algorithms.precisionDensityClustering, "" + Algorithms.recallDensityClustering});

        prResults.add(new String[]{"KMeans Based Clustering", ""});
        prResults.add(new String[]{"" + Algorithms.precisionKMeansClustering, "" + Algorithms.recallKMeansClustering});

        this.jTablePrecisionRecalls.setModel(new AbstractTableModel() {

            private String[] columns = new String[]{"PRECISION", "RECALLS"};

            public int getRowCount() {
                return prResults.size();
            }

            public int getColumnCount() {
                return columns.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return prResults.elementAt(rowIndex)[columnIndex];
            }

            @Override
            public String getColumnName(int column) {
                return this.columns[column];
            }
        });

        this.jInternalFramePrecisionRecalls.setVisible(true);

    }//GEN-LAST:event_jMenuItemPrecisionRecallsActionPerformed

    private void jMenuItemValidityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemValidityActionPerformed

        final Vector<String[]> validityResults = new Vector<String[]>();


        Double xebenisPartition = Algorithms.ValidityXIEBenis((Double[]) this.partitionClusteringResult[3], (Double[]) this.partitionClusteringResult[4], (Double[]) this.partitionClusteringResult[5], (Double) this.partitionClusteringResult[2]);
        Double fukuyamaPartition = Algorithms.ValidityFukuyamaSugenos((Double[]) this.partitionClusteringResult[3], (Double[]) this.partitionClusteringResult[4], (Double[]) this.partitionClusteringResult[5], (Double) this.partitionClusteringResult[2]);

        Double xebenisDensity = Algorithms.ValidityXIEBenis((Double[]) this.densityClusteringResult[3], (Double[]) this.densityClusteringResult[4], (Double[]) this.densityClusteringResult[5], (Double) this.densityClusteringResult[2]);
        Double fukuyamaDensity = Algorithms.ValidityFukuyamaSugenos((Double[]) this.densityClusteringResult[3], (Double[]) this.densityClusteringResult[4], (Double[]) this.densityClusteringResult[5], (Double) this.densityClusteringResult[2]);

        Double xebenisKMeans = Algorithms.ValidityXIEBenis((Double[]) this.kmeansClusteringResult[3], (Double[]) this.kmeansClusteringResult[4], (Double[]) this.kmeansClusteringResult[5], (Double) this.kmeansClusteringResult[2]);
        Double fukuyamaKMeans = Algorithms.ValidityFukuyamaSugenos((Double[]) this.kmeansClusteringResult[3], (Double[]) this.kmeansClusteringResult[4], (Double[]) this.kmeansClusteringResult[5], (Double) this.kmeansClusteringResult[2]);

        validityResults.add(new String[]{"Partition Based Clustering", ""});
        validityResults.add(new String[]{"" + xebenisPartition, "" + fukuyamaPartition});

        validityResults.add(new String[]{"Density Based Clustering", ""});
        validityResults.add(new String[]{"" + xebenisDensity, "" + fukuyamaDensity});

        validityResults.add(new String[]{"KMeans Based Clustering", ""});
        validityResults.add(new String[]{"" + xebenisKMeans, "" + fukuyamaKMeans});

        this.jTableValidity.setModel(new AbstractTableModel() {

            private String[] columns = new String[]{"XEIBENIS", "FUKUYAMA SUGENOS"};

            public int getRowCount() {
                return validityResults.size();
            }

            public int getColumnCount() {
                return columns.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return validityResults.elementAt(rowIndex)[columnIndex];
            }

            @Override
            public String getColumnName(int column) {
                return this.columns[column];
            }
        });

        this.jInternalFrameValidity.setVisible(true);


    }//GEN-LAST:event_jMenuItemValidityActionPerformed

    private void jMenuItemComparisonChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemComparisonChartActionPerformed

        final Vector<String[]> comparison = new Vector<String[]>();

        comparison.add(new String[]{"Partition Based Clustering", "", "", ""});
        comparison.add(new String[]{"Time Complexity:", "" + Algorithms.tcPClusteringStart + " (ms)", "" + Algorithms.tcPClusteringEnd + " (ms)", "" + Algorithms.tcPClusteringRun + " (ms)"});
        comparison.add(new String[]{"Space Complexity:", "" + Algorithms.scPClusteringStart + " (count)", "" + Algorithms.scPClusteringEnd + " (count)", "" + Algorithms.scPClusteringRun + " (count)"});

        comparison.add(new String[]{"Density Based Clustering", "", "", ""});
        comparison.add(new String[]{"Time Complexity:", "" + Algorithms.tcUClusteringStart + " (ms)", "" + Algorithms.tcUClusteringEnd + " (ms)", "" + Algorithms.tcUClusteringRun + " (ms)"});
        comparison.add(new String[]{"Space Complexity:", "" + Algorithms.scUClusteringStart + " (count)", "" + Algorithms.scUClusteringEnd + " (count)", "" + Algorithms.scUClusteringRun + " (count)"});

        comparison.add(new String[]{"KMeans Based Clustering", "", "", ""});
        comparison.add(new String[]{"Time Complexity:", "" + Algorithms.tcKClusteringStart + " (ms)", "" + Algorithms.tcKClusteringEnd + " (ms)", "" + Algorithms.tcKClusteringRun + " (ms)"});
        comparison.add(new String[]{"Space Complexity:", "" + Algorithms.scKClusteringStart + " (count)", "" + Algorithms.scKClusteringEnd + " (count)", "" + Algorithms.scKClusteringRun + " (count)"});

        this.jTableComparisonChart.setModel(new AbstractTableModel() {

            private String[] columns = new String[]{"Complexity Type", "Start (ms/count)", "End (ms/count)", "Run (ms/count)"};

            public int getRowCount() {
                return comparison.size();
            }

            public int getColumnCount() {
                return this.columns.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return comparison.elementAt(rowIndex)[columnIndex];
            }

            @Override
            public String getColumnName(int column) {
                return this.columns[column];
            }
        });

        this.jInternalFrameComparisonChart.setVisible(true);


    }//GEN-LAST:event_jMenuItemComparisonChartActionPerformed

    private void jMenuItemTimeComplexityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTimeComplexityActionPerformed

        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Partition Clustering", Algorithms.tcPClusteringRun);
        result.setValue("Density Clustering", Algorithms.tcUClusteringRun);
        result.setValue("KMeans Clustering", Algorithms.tcKClusteringRun);
        PieDataset dataset = result;
        // based on the dataset we create the chart

        JFreeChart chart = ChartFactory.createPieChart3D("Time Complexity Chart", // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        this.jInternalFrameTimeComplexityChart.getContentPane().removeAll();
        this.jInternalFrameTimeComplexityChart.getContentPane().add(chartPanel);

        this.jInternalFrameTimeComplexityChart.setVisible(true);

    }//GEN-LAST:event_jMenuItemTimeComplexityActionPerformed

    private void jMenuItemSpaceComplexityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSpaceComplexityActionPerformed
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Partition Clustering", Algorithms.scPClusteringRun);
        result.setValue("Density Clustering", Algorithms.scUClusteringRun);
        result.setValue("KMeans Clustering", Algorithms.scKClusteringRun);
        PieDataset dataset = result;
        // based on the dataset we create the chart

        JFreeChart chart = ChartFactory.createPieChart3D("Space Complexity Chart", // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        this.jInternalFrameSpaceComplexityChart.getContentPane().removeAll();
        this.jInternalFrameSpaceComplexityChart.getContentPane().add(chartPanel);

        this.jInternalFrameSpaceComplexityChart.setVisible(true);


    }//GEN-LAST:event_jMenuItemSpaceComplexityActionPerformed

    private void jMenuItemLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoginActionPerformed
        // TODO add your handling code here:
        jInternalFrameLogin.setVisible(true);
        jInternalFrameLogin.moveToFront();
}//GEN-LAST:event_jMenuItemLoginActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        jInternalFrameRegister.setVisible(true);
        jInternalFrameRegister.moveToFront();
}//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogoutActionPerformed
        // TODO add your handling code here:

        partitionApplied = false;
        densityApplied = false;
        kmeansApplied = false;
        
        jMenuItemLogin.setEnabled(true);
        jMenuItemPartitionClustering.setEnabled(false);
        jMenuItemDensityClustering.setEnabled(false);
        jMenuItemKMeansClustering.setEnabled(false);
        jMenuItemPrecisionRecalls.setEnabled(false);
        jMenuItemValidity.setEnabled(false);
        jMenuItemComparisonChart.setEnabled(false);
        jMenuItemWhetherDatabase.setEnabled(false);
        jMenuItemLogout.setEnabled(false);
        
        if (jInternalFramePartitionClustering.isVisible()) {
            jInternalFramePartitionClustering.setVisible(false);
        }
        if (jInternalFrameDensityClustering.isVisible()) {
            jInternalFrameDensityClustering.setVisible(false);
        }
        if (jInternalFrameKMeansClustering.isVisible()) {
            jInternalFrameKMeansClustering.setVisible(false);
        }
        if (jInternalFramePrecisionRecalls.isVisible()) {
            jInternalFramePrecisionRecalls.setVisible(false);
        }
        if (jInternalFrameValidity.isVisible()) {
            jInternalFrameValidity.setVisible(false);
        }
        if (jInternalFrameSpaceComplexityChart.isVisible()) {
            jInternalFrameSpaceComplexityChart.setVisible(false);
        }
        if (jInternalFrameTimeComplexityChart.isVisible()) {
            jInternalFrameTimeComplexityChart.setVisible(false);
        }
        if (jInternalFrameComparisonChart.isVisible()) {
            jInternalFrameComparisonChart.setVisible(false);
        }

        jProgressBarProgress.setValue(0);
}//GEN-LAST:event_jMenuItemLogoutActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        String username = jTextFieldUsername.getText();
        String password = jPasswordFieldPassword.getText();
        boolean valid = DBUtils.login(username, password);
        if (valid) {
            jMenuItemPartitionClustering.setEnabled(true);
            jMenuItemDensityClustering.setEnabled(true);
            jMenuItemKMeansClustering.setEnabled(true);
//            jMenuItemPrecisionRecalls.setEnabled(true);
//            jMenuItemValidity.setEnabled(true);
//            jMenuItemComparisonChart.setEnabled(true);
            jMenuItemWhetherDatabase.setEnabled(true);
            jMenuItemLogin.setEnabled(false);
            jMenuItemLogout.setEnabled(true);
            JOptionPane.showMessageDialog(jButtonLogin, "Login Success");
            jInternalFrameLogin.setVisible(false);
            jPasswordFieldPassword.setText("");
            jTextFieldUsername.setText("");
        } else {
            JOptionPane.showMessageDialog(jButtonLogin, "Login Failed");
        }
}//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed
        // TODO add your handling code here:

        String name = jTextFieldRegisterName.getText();
        String email = jTextFieldRegisterEmail.getText();
        String designation = jTextFieldRegisterDesignation.getText();
        String username = jTextFieldRegisterUsername.getText();
        String password = jPasswordFieldRegisterPassword.getText();
        //String rpassword=jPasswordFieldRegisterRPassword.getText();
        if (name.equals("")) {

            JOptionPane.showMessageDialog(jButtonLogin, "Invalid details");

            jTextFieldRegisterName.requestFocus();

        } else if (designation.equals("")) {
            JOptionPane.showMessageDialog(jButtonLogin, "Invalid details");
            jTextFieldRegisterDesignation.requestFocus();

        } else if (username.equals("")) {
            JOptionPane.showMessageDialog(jButtonLogin, "Invalid details");
            jTextFieldRegisterUsername.requestFocus();

        } else if (password.equals("")) {
            JOptionPane.showMessageDialog(jButtonLogin, "Invalid details");
            jPasswordFieldRegisterPassword.requestFocus();

        } else {

            int atpos = email.indexOf("@");
            int dotpos = email.indexOf(".");

            if ((atpos < 1) || (atpos > dotpos) || (dotpos <= atpos + 2) || (atpos == -1) || (dotpos == -1)) {
                JOptionPane.showMessageDialog(jButtonLogin, "Invalid Email ID");
                jTextFieldRegisterEmail.requestFocus();
            } else {
                boolean registered = DBUtils.register(name, email, designation, username, password);
                if (registered) {
                    JOptionPane.showMessageDialog(jButtonLogin, "Registration Success");
                    jInternalFrameRegister.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(jButtonLogin, "Registration Failed");
                }
            }
        }
}//GEN-LAST:event_jButtonRegisterActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jInternalFramePartitionClusteringInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFramePartitionClusteringInternalFrameClosing
        // TODO add your handling code here:

        partitionClosed = true;

        if (partitionClosed && densityClosed && kmeansClosed) {
            jProgressBarProgress.setValue(0);
        }
    }//GEN-LAST:event_jInternalFramePartitionClusteringInternalFrameClosing

    private void jInternalFrameDensityClusteringInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrameDensityClusteringInternalFrameClosing
        // TODO add your handling code here:
        densityClosed = true;

        if (partitionClosed && densityClosed && kmeansClosed) {
            jProgressBarProgress.setValue(0);
        }
    }//GEN-LAST:event_jInternalFrameDensityClusteringInternalFrameClosing

    private void jInternalFrameKMeansClusteringInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrameKMeansClusteringInternalFrameClosing
        // TODO add your handling code here:
        kmeansClosed = true;

        if (partitionClosed && densityClosed && kmeansClosed) {
            jProgressBarProgress.setValue(0);
        }
    }//GEN-LAST:event_jInternalFrameKMeansClusteringInternalFrameClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JInternalFrame jInternalFrameComparisonChart;
    private javax.swing.JInternalFrame jInternalFrameDensityClustering;
    private javax.swing.JInternalFrame jInternalFrameKMeansClustering;
    private javax.swing.JInternalFrame jInternalFrameLogin;
    private javax.swing.JInternalFrame jInternalFramePartitionClustering;
    private javax.swing.JInternalFrame jInternalFramePrecisionRecalls;
    private javax.swing.JInternalFrame jInternalFrameProgress;
    private javax.swing.JInternalFrame jInternalFrameRegister;
    private javax.swing.JInternalFrame jInternalFrameSpaceComplexityChart;
    private javax.swing.JInternalFrame jInternalFrameTimeComplexityChart;
    private javax.swing.JInternalFrame jInternalFrameValidity;
    private javax.swing.JInternalFrame jInternalFrameWheatherDatabase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemComparisonChart;
    private javax.swing.JMenuItem jMenuItemDensityClustering;
    private javax.swing.JMenuItem jMenuItemKMeansClustering;
    private javax.swing.JMenuItem jMenuItemLogin;
    private javax.swing.JMenuItem jMenuItemLogout;
    private javax.swing.JMenuItem jMenuItemPartitionClustering;
    private javax.swing.JMenuItem jMenuItemPrecisionRecalls;
    private javax.swing.JMenuItem jMenuItemSpaceComplexity;
    private javax.swing.JMenuItem jMenuItemTimeComplexity;
    private javax.swing.JMenuItem jMenuItemValidity;
    private javax.swing.JMenuItem jMenuItemWhetherDatabase;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JPasswordField jPasswordFieldRegisterPassword;
    private javax.swing.JProgressBar jProgressBarProgress;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTableComparisonChart;
    private javax.swing.JTable jTableDensityClustering;
    private javax.swing.JTable jTableKMeansClustering;
    private javax.swing.JTable jTablePartitionClustering;
    private javax.swing.JTable jTablePrecisionRecalls;
    private javax.swing.JTable jTableValidity;
    private javax.swing.JTable jTableWheatherDatabase;
    private javax.swing.JTextField jTextFieldRegisterDesignation;
    private javax.swing.JTextField jTextFieldRegisterEmail;
    private javax.swing.JTextField jTextFieldRegisterName;
    private javax.swing.JTextField jTextFieldRegisterUsername;
    private javax.swing.JTextField jTextFieldUsername;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
