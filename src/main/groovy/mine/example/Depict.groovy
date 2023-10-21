package mine.example

import org.openscience.cdk.layout.StructureDiagramGenerator
import org.openscience.cdk.renderer.AtomContainerRenderer
import org.openscience.cdk.renderer.font.AWTFontManager
import org.openscience.cdk.renderer.generators.BasicAtomGenerator
import org.openscience.cdk.renderer.generators.BasicBondGenerator
import org.openscience.cdk.renderer.generators.BasicSceneGenerator
import org.openscience.cdk.renderer.generators.BasicSceneGenerator.ZoomFactor
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor
import org.openscience.cdk.silent.SilentChemObjectBuilder
import org.openscience.cdk.smiles.SmilesParser

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.awt.image.RenderedImage
import java.util.List

smiles = "CN1C=NC2=C1C(=O)N(C(=O)N2C)C"
int WIDTH = 200
int HEIGHT = 250
Rectangle drawArea = new Rectangle(WIDTH, HEIGHT)
Image image = new BufferedImage(
        WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB
)
smilesParser = new SmilesParser(
        SilentChemObjectBuilder.getInstance()
)
molecule = smilesParser.parseSmiles(smiles)
StructureDiagramGenerator sdg =
        new StructureDiagramGenerator()
sdg.setMolecule(molecule)
sdg.generateCoordinates()
molecule = sdg.getMolecule()
List generators =
        new ArrayList()
generators.add(new BasicSceneGenerator())
generators.add(new BasicBondGenerator())
generators.add(new BasicAtomGenerator())
AtomContainerRenderer renderer =
        new AtomContainerRenderer(
                generators, new AWTFontManager()
        )
renderer.setup(molecule, drawArea)
model = renderer.getRenderer2DModel()
model.set(ZoomFactor.class, (double) 0.9)
Graphics2D g2 = (Graphics2D) image.getGraphics()
g2.setColor(Color.WHITE)
g2.fillRect(0, 0, WIDTH, HEIGHT)
renderer.paint(molecule, new AWTDrawVisitor(g2))
ImageIO.write(
        (RenderedImage) image, "PNG",
        new File("CTR2.png")
)