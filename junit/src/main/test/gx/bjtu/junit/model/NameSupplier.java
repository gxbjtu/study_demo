package gx.bjtu.junit.model;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jean on 16-3-27.
 */
public class NameSupplier extends ParameterSupplier {
    @Override
    public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
        PotentialAssignment nameAssignment1 = PotentialAssignment.forValue("name", "jean");
        PotentialAssignment nameAssignment2 = PotentialAssignment.forValue("name", "gx");
        return Arrays.asList(new PotentialAssignment[]{nameAssignment1, nameAssignment2});
    }
}
